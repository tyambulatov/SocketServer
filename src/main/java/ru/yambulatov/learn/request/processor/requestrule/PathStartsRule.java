package ru.yambulatov.learn.request.processor.requestrule;

import ru.yambulatov.learn.request.HttpRequest;

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
