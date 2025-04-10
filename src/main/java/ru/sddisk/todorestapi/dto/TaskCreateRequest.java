package ru.sddisk.todorestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
        @NotEmpty(message = "Title cannot be empty or null")
        String title,
        @NotNull(message = "Description cannot be null")
        @Size(max = 1000)
        String description) {
}