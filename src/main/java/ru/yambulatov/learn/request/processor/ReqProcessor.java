package ru.yambulatov.learn.request.processor;

import ru.yambulatov.learn.request.HttpRequest;

public interface ReqProcessor {
    void process(HttpRequest httpRequest);
}
