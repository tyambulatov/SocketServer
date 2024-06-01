package org.example.exception;

public class NoContentException extends RuntimeException {

    public NoContentException(String message) {
        super(message);
    }
    public NoContentException(Exception exception) {
        super(exception);
    }
    public NoContentException(String message, Exception e) {
        super(message, e);
    }
    public NoContentException() {}
}
