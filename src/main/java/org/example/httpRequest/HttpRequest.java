package org.example.httpRequest;

import org.example.exception.FailedToReadRequestBody;
import org.example.exception.FailedToReadRequestHead;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class HttpRequest {

    public String getProtocol() {
        return protocol;
    }

    public enum Method {
        GET,
        PUT,
        POST,
        DELETE;
    }

    private Method method;
    private String protocol;
    private String path;
    private final Map<String, String> parameters = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private String body;
    private final Socket socket;
    private BufferedReader bufferedReader;

    public HttpRequest(Socket socket) {
        this.socket = socket;
        List<String> requestHeadLines = parseRequestHeadLines();
        checkRequestHeadNotEmpty(requestHeadLines);
        parse(requestHeadLines);
    }

    private List<String> parseRequestHeadLines() {
        List<String> stringList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                stringList.add(line);
            }
        } catch (IOException e) {
            throw new FailedToReadRequestHead(e);
        }
        return stringList;
    }

    private void checkRequestHeadNotEmpty(List<String> httpRequest) {
        if (httpRequest.isEmpty()) throw new IllegalArgumentException("Empty request");
    }

    private void parse(List<String> requestLines) {
        startLineParsing(requestLines.remove(0));
        if (!requestLines.isEmpty()) {
            requestHeadersParsing(requestLines);
        }
    }

    private void startLineParsing(String requestLine) {
        String[] requestLineParts = requestLine.split(" ");

        method = Method.valueOf(requestLineParts[0]);
        targetParsing(requestLineParts[1]);
        protocol = requestLineParts[2];
    }

    private void targetParsing(String url) {
        String[] strings = url.split("\\?");
        path = strings[0];

        if (strings.length > 1) {
            String urlParameters = strings[1];
            String[] parametersEqualsValue = urlParameters.split("&");

            for (String s : parametersEqualsValue) {
                String[] parameterValue = s.split("=");
                parameters.put(parameterValue[0], parameterValue[1]);
            }
        }
    }

    private void requestHeadersParsing(List<String> requestLines) {
        for (int i = 0; i < requestLines.size(); i++) {
            String[] headerParts = requestLines.get(i).split(": ");
            headers.put(headerParts[0], headerParts[1]);
        }
    }

    public boolean haveBody() {
        return headers.containsKey("Content-Length") && headers.containsKey("Content-Type");
    }

    public Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        if (body == null && haveBody()) {
            char[] buffer = new char[2048];
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            try {
                int charsRead = bufferedReader.read(buffer, 0, contentLength);
                body = new String(buffer, 0, charsRead);
            } catch (IOException e) {
                throw new FailedToReadRequestBody(e);
            }
        }
        return body;
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }

    public List<String> getAcceptTypes() {
        return Arrays.stream(headers.get("Accept").split(", ")).toList();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        getBody();
        return "HttpRequest{\r\n" +
                "method=" + method +
                ",\r\nprotocol='" + protocol + '\'' +
                ",\r\npath='" + path + '\'' +
                ",\r\nparameters=" + parameters +
                ",\r\nheaders=" + headers +
                ",\r\nbody=" + body +
                "}\r\n";
    }
}
