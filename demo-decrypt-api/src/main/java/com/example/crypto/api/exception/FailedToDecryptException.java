package com.example.crypto.api.exception;

public class FailedToDecryptException extends RuntimeException {

    public FailedToDecryptException(Throwable cause) {
        super("Failed to decrypt text", cause);
    }

}