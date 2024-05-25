package org.example.requestProcessor;

import org.example.httpRequest.HttpRequest;

public class PathRequestRule implements RequestRule {

    private final String pathPart;

    public PathRequestRule(String pathPart) {
        this.pathPart = pathPart;
    }

    @Override
    public boolean matches(HttpRequest request) {
        return request.getPath().startsWith(pathPart);
    }
}
