package org.example.requestProcessor;

import org.example.httpRequest.HttpRequest;

public interface RequestRule {
    boolean matches(HttpRequest request);
}
