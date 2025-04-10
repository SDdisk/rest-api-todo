package ru.sddisk.todorestapi.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sddisk.todorestapi.exception.TaskAlreadyExistsException;
import ru.sddisk.todorestapi.exception.TaskNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> "error - " + fieldError.getDefaultMessage())
                .orElse("Validation error");

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMsg
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
                        ex.getMessage()
                ),
                HttpStatus.NOT_FOUND
        );
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorResponse> handleUniqueConstraintException(
//            DataIntegrityViolationException ex
//    ) {
//        String errorMsg = "Error saving data";
//
//        Throwable rootCause = ex.getRootCause();
//        if (rootCause != null && rootCause.getMessage().contains("unique constraint")) {
//            errorMsg = "Task with this title already exists";
//        }
//
//        return new ResponseEntity<>(
//                new ErrorResponse(
//                        HttpStatus.CONFLICT.value(),
//                        errorMsg
//                ),
//                HttpStatus.CONFLICT
//        );
//    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTaskAlreadyExistsException(
            TaskAlreadyExistsException ex
    ) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        ex.getMessage()
                ),
                HttpStatus.CONFLICT
        );
    }

    public record ErrorResponse(int status, String message) {}
}
