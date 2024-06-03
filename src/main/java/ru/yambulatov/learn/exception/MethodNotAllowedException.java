package ru.yambulatov.learn.exception;

public class MethodNotAllowedException extends RuntimeException {

    public MethodNotAllowedException(String message) {
        super(message);
    }
    public MethodNotAllowedException(Exception exception) {
        super(exception);
    }
    public MethodNotAllowedException(String message, Exception e) {
        super(message, e);
    }
    public MethodNotAllowedException() {}
}
