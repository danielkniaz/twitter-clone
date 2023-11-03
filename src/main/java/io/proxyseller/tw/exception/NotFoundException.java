package io.proxyseller.tw.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("entity with id " + id + " not found.");
    }
}
