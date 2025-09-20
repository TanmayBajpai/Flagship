package com.flagship.backend.Exceptions;

public class InvalidFeatureFlag extends RuntimeException {
    public InvalidFeatureFlag() {
        super("One or more attributes of the Feature Flag is invalid");
    }
}
