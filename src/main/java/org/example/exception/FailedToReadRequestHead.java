package org.example.exception;

public class FailedToReadRequestHead extends RuntimeException {
    public FailedToReadRequestHead(Exception e) {
        super(e);
    }
}
