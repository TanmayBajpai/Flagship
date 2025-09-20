package com.flagship.backend.Exceptions;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException() {
        super("Username is already taken");
    }
}
