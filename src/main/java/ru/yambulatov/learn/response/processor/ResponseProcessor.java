package ru.yambulatov.learn.response.processor;

import jakarta.ws.rs.core.Response;
import ru.yambulatov.learn.request.HttpRequest;

public interface    ResponseProcessor {
    void returnBody(HttpRequest httpRequest, Response.Status status, Object anything);

    void returnNoBody(HttpRequest httpRequest, Response.Status status);
}
