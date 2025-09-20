package com.flagship.backend.Exceptions;

public class InvalidApiKeyException extends RuntimeException {
    public InvalidApiKeyException() {
        super("Invalid API Key");
    }
}
