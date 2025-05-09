package ru.sddisk.todorestapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sddisk.todorestapi.store.entity.Task;
import ru.sddisk.todorestapi.store.entity.enums.TaskStatus;

public record TaskDTO(
        Long id,

        @NotBlank(message = "Title cannot be empty or null")
        @Size(
                max = 255,
                message = "The title cannot exceed 255 characters in length"
        )
        String title,

        @Size(
                max = 5000,
                message = "The description cannot exceed 5000 characters in length"
        )
        String description,

        @NotNull(message = "Status cannot be null")
        TaskStatus status
) {
        public static TaskDTO fromEntity(Task task) {
                return new TaskDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus()
                );
        }
}
