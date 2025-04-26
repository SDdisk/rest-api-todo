package ru.sddisk.todorestapi.exception;

public class InvalidTaskStatusException extends RuntimeException {
    public InvalidTaskStatusException(String message) {
        super(message);
    }
}
