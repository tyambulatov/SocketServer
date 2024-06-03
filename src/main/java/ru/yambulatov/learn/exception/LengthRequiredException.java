package ru.yambulatov.learn.exception;

public class LengthRequiredException extends RuntimeException {

    public LengthRequiredException(String message) {
        super(message);
    }
    public LengthRequiredException(Exception exception) {
        super(exception);
    }
    public LengthRequiredException(String message, Exception e) {
        super(message, e);
    }
    public LengthRequiredException() {}
}
