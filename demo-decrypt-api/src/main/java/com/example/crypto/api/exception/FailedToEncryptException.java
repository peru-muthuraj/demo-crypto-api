package com.example.crypto.api.exception;

public class FailedToEncryptException extends RuntimeException {

    public FailedToEncryptException(Throwable cause) {
        super("Failed to encrypt text", cause);
    }

}