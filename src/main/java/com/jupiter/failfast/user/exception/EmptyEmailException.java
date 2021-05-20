package com.jupiter.failfast.user.exception;

public class EmptyEmailException extends RuntimeException {

    public EmptyEmailException(String message) {
        super(message);
    }
}
