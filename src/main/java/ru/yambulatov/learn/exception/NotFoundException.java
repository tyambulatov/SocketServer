package ru.yambulatov.learn.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Exception e) {
        super(message, e);
    }
    public NotFoundException() {}
}
