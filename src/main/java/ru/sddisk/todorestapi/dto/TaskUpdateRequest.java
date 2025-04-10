package ru.sddisk.todorestapi.dto;

import jakarta.validation.constraints.Size;

public record TaskUpdateRequest(
        @Size(min = 1, message = "Title cannot be empty")
        String title,
        String description) {
}
