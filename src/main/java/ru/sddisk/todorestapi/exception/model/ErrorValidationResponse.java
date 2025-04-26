package ru.sddisk.todorestapi.exception.model;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorValidationResponse(
        String message,
        List<String> errors,
        LocalDateTime timestamp
) {}