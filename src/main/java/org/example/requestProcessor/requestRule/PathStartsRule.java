package org.example.requestProcessor.requestRule;

import org.example.httpRequest.HttpRequest;

public class PathStartsRule implements RequestRule {

    private final String pathPart;

    public PathStartsRule(String pathPart) {
        this.pathPart = pathPart;
    }

    @Override
    public boolean matches(HttpRequest request) {
        return request.getPath().startsWith(pathPart);
    }
}
