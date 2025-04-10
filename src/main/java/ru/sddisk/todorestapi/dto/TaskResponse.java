package ru.sddisk.todorestapi.dto;

public record TaskResponse(
        Long id,
        String title,
        String description) {
}