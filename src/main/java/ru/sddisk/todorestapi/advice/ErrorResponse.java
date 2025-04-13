package ru.sddisk.todorestapi.advice;

public record ErrorResponse(int status, String message) {}
