package ru.sddisk.todorestapi.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sddisk.todorestapi.exception.InvalidTaskStatusException;
import ru.sddisk.todorestapi.exception.TaskAlreadyExistException;
import ru.sddisk.todorestapi.exception.model.ErrorResponse;
import ru.sddisk.todorestapi.exception.model.ErrorValidationResponse;
import ru.sddisk.todorestapi.exception.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorValidationResponse handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorValidationResponse errorResponse =
                new ErrorValidationResponse("Validation failed", errorMessages, LocalDateTime.now());

        logThis(ex, ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(InvalidTaskStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTaskStatusException(
            InvalidTaskStatusException ex
    ) {
        logThis(ex, ex.getMessage());
        return new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFoundException(
            TaskNotFoundException ex
    ) {
        logThis(ex, ex.getMessage());
        return new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(TaskAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleTaskAlreadyExistException(
            TaskAlreadyExistException ex
    ) {
        logThis(ex, ex.getMessage());
        return new ErrorResponse(ex.getMessage(), LocalDateTime.now());
    }

    private void logThis(Exception exception, String message) {
        log.error(
                "Handled exception: '{}' | Message: '{}'",
                exception.getClass().getSimpleName(),
                message,
                exception
        );
    }
}