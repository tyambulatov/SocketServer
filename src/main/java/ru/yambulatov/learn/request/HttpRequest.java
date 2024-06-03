package ru.yambulatov.learn.request;

import ru.yambulatov.learn.exception.BadRequestException;
import ru.yambulatov.learn.exception.LengthRequiredException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class HttpRequest {

    public enum Method {
        GET,
        PUT,
        POST,
        DELETE

    }
    private Method method;
    private String protocol;
    private String path;
    private final Map<String, String> parameters;
    private final Map<String, String> headers;
    private String body;
    private final Socket socket;
    private BufferedReader bufferedReader;

    public HttpRequest(Socket socket) {
        this.parameters = new HashMap<>();
        this.headers = new HashMap<>();
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
            throw new BadRequestException(e);
        }
        return stringList;
    }

    private void checkRequestHeadNotEmpty(List<String> httpRequest) {
        if (httpRequest.isEmpty()) throw new BadRequestException();
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
        for (String requestLine : requestLines) {
            String[] headerParts = requestLine.split(": ");
            headers.put(headerParts[0], headerParts[1]);
        }
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
        if (body == null) {
            hadBody();
            readBody();
        }
        return body;
    }

    private void hadBody() {
        if (!(headers.containsKey("Content-Length") && headers.containsKey("Content-Type"))) {
            throw new LengthRequiredException();
        }
    }

    private void readBody() {
        char[] buffer = new char[2048];
        int contentLength = Integer.parseInt(headers.get("Content-Length"));
        try {
            int charsRead = bufferedReader.read(buffer, 0, contentLength);
            body = new String(buffer, 0, charsRead);
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }

    public List<String> getAcceptHeaders() {
        String[] f = headers.get("Accept").split(",");
        return Arrays.stream(f).toList();
    }

    public String getProtocol() {
        return protocol;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", protocol='" + protocol + '\'' +
                ", path='" + path + '\'' +
                ", parameters=" + parameters +
                ", headers=" + headers +
                ", body='" + getBody() + '\'' +
                ", socket=" + socket +
                '}';
    }
}
