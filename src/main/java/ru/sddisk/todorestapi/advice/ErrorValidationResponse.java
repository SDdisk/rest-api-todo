package ru.sddisk.todorestapi.advice;

import java.util.List;

public record ErrorValidationResponse(int status, String message, List<String> errors) {}