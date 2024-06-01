package org.example.response;

import jakarta.ws.rs.core.Response;
import org.example.httpRequest.HttpRequest;

public interface    ResponseProcessor {
    void returnBody(HttpRequest httpRequest, Response.Status status, Object anything);

    void returnNoBody(HttpRequest httpRequest, Response.Status status);
}
