package ru.yambulatov.learn.request.processor.requestrule;

import ru.yambulatov.learn.request.HttpRequest;

public interface RequestRule {
    boolean matches(HttpRequest request);
}
