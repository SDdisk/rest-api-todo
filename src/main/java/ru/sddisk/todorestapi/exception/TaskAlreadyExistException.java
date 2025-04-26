package ru.sddisk.todorestapi.exception;

public class TaskAlreadyExistException extends RuntimeException {
    public TaskAlreadyExistException(String message) {
        super(message);
    }
}
