package org.example.requestProcessor.requestRule;

import org.example.httpRequest.HttpRequest;

public interface RequestRule {
    boolean matches(HttpRequest request);
}
