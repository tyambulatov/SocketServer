package org.example.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(Exception exception) {
        super(exception);
    }
    public BadRequestException(String message, Exception e) {
        super(message, e);
    }
    public BadRequestException() {}
}
