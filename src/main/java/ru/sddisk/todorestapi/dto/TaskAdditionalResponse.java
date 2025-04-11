package ru.sddisk.todorestapi.dto;

import java.time.LocalDateTime;

public record TaskAdditionalResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
