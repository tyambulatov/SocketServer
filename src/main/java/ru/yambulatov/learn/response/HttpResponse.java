package ru.yambulatov.learn.response;

import jakarta.ws.rs.core.Response;
import ru.yambulatov.learn.request.HttpRequest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpResponse {

    private final String protocol;
    private final Response.Status status;
    private final String contentType;
    private final Map<String, String> headers;
    private final Object body;

    public HttpResponse(List<String> allowedMimeTypes, HttpRequest httpRequest, Response.Status status, Object body) {
        this.protocol = httpRequest.getProtocol();
        this.status = status;
        this.contentType = setContentType(allowedMimeTypes, httpRequest);
        this.headers = setHeaders();
        this.body = body;
    }

    private String setContentType(List<String> allowedMimeTypes, HttpRequest httpRequest) {
        String defaultContentType = "text/html";
        List<String> acceptHeaders = httpRequest.getAcceptHeaders();

        if (acceptHeaders.isEmpty() || acceptHeaders.contains("*/*")) {
            return defaultContentType;
        }

        return acceptHeaders.stream().filter(allowedMimeTypes::contains).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported MIME types: " + acceptHeaders));
    }

    private Map<String, String> setHeaders() {
        return Map.of("Content-Type", contentType);
    }

    public byte[] toByteArray() {
        byte[] statusLineBytes = buildStatusLine();
        byte[] headersBytes = buildHeadersLines();

        return  body == null
                ? responseBytesNoBody(statusLineBytes, headersBytes)
                : responseBytesWithBody(statusLineBytes, headersBytes);
    }

    private byte[] responseBytesNoBody(byte[] statusLineBytes, byte[] headersBytes) {
        byte[] result = new byte[responseBytesLength(statusLineBytes,headersBytes, null)];
        ByteBuffer.wrap(result)
                .put(statusLineBytes)
                .put(headersBytes)
                .put("\r\n".getBytes(StandardCharsets.UTF_8));
        return result;
    }
    
    private int responseBytesLength(byte[] statusLineBytes, byte[] headersBytes, byte[] bodyBytes) {
        return bodyBytes == null
                ? statusLineBytes.length + headersBytes.length + 4
                : statusLineBytes.length + headersBytes.length + 4 + bodyBytes.length;
    }

    private byte[] responseBytesWithBody(byte[] statusLineBytes, byte[] headersBytes) {
        byte[] bodyBytes = BodyBuilder.build(contentType, body);
        byte[] result = new byte[responseBytesLength(statusLineBytes,headersBytes, bodyBytes)];
        ByteBuffer.wrap(result)
                .put(statusLineBytes)
                .put(headersBytes)
                .put("\r\n".getBytes(StandardCharsets.UTF_8))
                .put(bodyBytes);
        return result;
    }

    private byte[] buildStatusLine() {
        return (protocol + " " + status.getStatusCode() + " " + status + "\r\n").getBytes(StandardCharsets.UTF_8);
    }

    private byte[] buildHeadersLines() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }

        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
