package org.example.exception;

public class NotFoundException extends RuntimeException {

    // TODO: все эксепшены примерно по образцу этого
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Exception e) {
        super(message, e);
    }

    public NotFoundException() {}
}
