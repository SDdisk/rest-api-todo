package ru.sddisk.todorestapi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sddisk.todorestapi.exception.TaskAlreadyExistsException;
import ru.sddisk.todorestapi.exception.TaskNotFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errorMessages
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(
            TaskNotFoundException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        "Entity not exists",
                        List.of(ex.getMessage())
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTaskAlreadyExistsException(
            TaskAlreadyExistsException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "Entity already exists",
                        List.of(ex.getMessage())
                ),
                HttpStatus.CONFLICT
        );
    }

    public record ErrorResponse(int status, String message, List<String> errors) {}
}
