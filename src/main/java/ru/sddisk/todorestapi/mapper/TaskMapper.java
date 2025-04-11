package ru.sddisk.todorestapi.mapper;

import ru.sddisk.todorestapi.dto.TaskAdditionalResponse;
import ru.sddisk.todorestapi.dto.TaskCreateRequest;
import ru.sddisk.todorestapi.dto.TaskResponse;
import ru.sddisk.todorestapi.dto.TaskUpdateRequest;
import ru.sddisk.todorestapi.model.Task;

import java.util.List;

public class TaskMapper {

    /*
        TO ENTITY
     */

    // create request -> task
    public static Task toEntity(TaskCreateRequest req) {
        return new Task(
                req.title(),
                req.description()
        );
    }

    // update request -> task
    public static Task toEntity(TaskUpdateRequest req) {
        return new Task(
                req.title(),
                req.description()
        );
    }

    /*
        TO RESPONSE (RESPONSES)
     */

    // task -> response
    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription()
        );
    }

    // tasks -> responses
    public static List<TaskResponse> toResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    // task -> additional response
    public static TaskAdditionalResponse toAdditionalResponse(Task task) {
        return new TaskAdditionalResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    // task -> additional responses
    public static List<TaskAdditionalResponse> toAdditionalResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::toAdditionalResponse)
                .toList();
    }
}
