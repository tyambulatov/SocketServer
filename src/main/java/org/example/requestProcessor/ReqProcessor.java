package org.example.requestProcessor;

import org.example.httpRequest.HttpRequest;

public interface ReqProcessor {
    void process(HttpRequest httpRequest);
}
