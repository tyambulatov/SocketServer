package org.example.exception;

public class FailedToAcceptConnection extends RuntimeException {
    public FailedToAcceptConnection(Exception e) {
        super(e);
    }
}
