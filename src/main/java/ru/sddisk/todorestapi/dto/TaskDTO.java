package ru.sddisk.todorestapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.sddisk.todorestapi.model.Task;

public record TaskDTO(
        Long id,
        @NotBlank(message = "Title cannot be empty or null")
        String title,
        @Size(
                max = 5000,
                message = "The description cannot exceed 5000 characters in length"
        )
        String description
) {
        public static TaskDTO fromEntity(Task task) {
                return new TaskDTO(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription()
                );
        }
}
