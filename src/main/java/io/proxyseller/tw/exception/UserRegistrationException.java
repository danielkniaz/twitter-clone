package io.proxyseller.tw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String field, String value) {
        super(String.format("Cannot register a new user. Field %s with value %s already exist", field, value));
    }
}
