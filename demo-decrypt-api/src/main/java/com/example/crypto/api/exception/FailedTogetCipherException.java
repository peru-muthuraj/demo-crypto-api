package com.example.crypto.api.exception;

public class FailedTogetCipherException extends Exception {

    public FailedTogetCipherException(Throwable cause) {
        super("Failed to get Cipher", cause);
    }
}
