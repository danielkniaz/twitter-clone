package io.proxyseller.tw.exception;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String field, String value) {
        super(String.format("Cannot register a new user. Field %s with value %s already exist", field, value));
    }
}
