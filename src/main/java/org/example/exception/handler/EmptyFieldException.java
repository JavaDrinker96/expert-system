package org.example.exception.handler;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String message) {
        super(message);
    }
}
