package ru.sddisk.todorestapi.dto;

import ru.sddisk.todorestapi.store.entity.enums.TaskStatus;

public record TaskFilterDTO(
        TaskStatus status
) {}