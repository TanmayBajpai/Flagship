package com.flagship.backend.Exceptions;

public class InvalidFlagIdException extends RuntimeException {
    public InvalidFlagIdException() {
        super("Invalid Feature Flag Id");
    }
}
