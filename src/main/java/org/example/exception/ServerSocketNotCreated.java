package org.example.exception;

public class ServerSocketNotCreated extends RuntimeException {
    public ServerSocketNotCreated(Exception e) {
        super(e);
    }
}
