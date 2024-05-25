package org.example.exception;

public class FailedToReadRequestBody extends RuntimeException {
    public FailedToReadRequestBody(Exception e) {
        super(e);
    }
}
