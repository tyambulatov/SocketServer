package org.example.response;

import jakarta.ws.rs.core.Response;
import org.example.httpRequest.HttpRequest;

import java.nio.ByteBuffer;
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
        String defaultAcceptHeader = "text/html"; // Default MIME type
        String acceptHeader = httpRequest.getAcceptHeaders().get(0);

        if (acceptHeader == null) {
            acceptHeader = defaultAcceptHeader;
        } else if (!allowedMimeTypes.contains(acceptHeader)) {
            throw new IllegalArgumentException("Unsupported MIME type: " + acceptHeader);
        }

        if (acceptHeader.equals("*/*")) {
            acceptHeader = defaultAcceptHeader;
        }

        return acceptHeader;
    }

    private Map<String, String> setHeaders() {
        return Map.of("Content-Type", contentType);
    }

    public byte[] toByteArray() {
        byte[] statusLineBytes = buildStatusLine();
        byte[] headersBytes = buildHeadersLines();
        byte[] bodyBytes = BodyBuilder.build(contentType, body);

        byte[] result = new byte[statusLineBytes.length + headersBytes.length + 4 + bodyBytes.length];

        ByteBuffer byteBuffer = ByteBuffer.wrap(result);
        byteBuffer.put(statusLineBytes)
                .put(headersBytes)
                .put("\r\n".getBytes())
                .put(bodyBytes);

        return  result;
    }

    private byte[] buildStatusLine() {
        return (protocol + " " + status.getStatusCode() + " " + status + "\r\n").getBytes();
    }

    private byte[] buildHeadersLines() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var entry : headers.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }

        return stringBuilder.toString().getBytes();
    }
}
