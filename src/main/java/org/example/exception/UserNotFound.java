package org.example.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound(Exception e) {
        super(e);
    }

    public UserNotFound() {}
}
