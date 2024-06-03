package ru.yambulatov.learn.response;

import java.nio.charset.StandardCharsets;

public class BodyBuilder {
    public static byte[] build(String contentType, Object body) {
        byte[] result;
        switch (contentType) {
            case "text/html" -> result = getHtmlView(body);
            case "application/json" -> result = getJsonView(body);
            default -> throw new IllegalArgumentException("Unsupported content type: " + contentType);
        }
        return result;
    }

    private static byte[] getJsonView(Object body) {
        // TODO create implementation
        return null;
    }

    private static byte[] getHtmlView(Object body) {
        // TODO create implementation
        return body.toString().getBytes(StandardCharsets.UTF_8);
    }
}
