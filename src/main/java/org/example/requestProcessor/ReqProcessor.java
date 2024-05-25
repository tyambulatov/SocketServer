package org.example.requestProcessor;

import org.example.httpRequest.HttpRequest;

import java.io.IOException;

public interface ReqProcessor {
    void process(HttpRequest httpRequest) throws IOException;
}
