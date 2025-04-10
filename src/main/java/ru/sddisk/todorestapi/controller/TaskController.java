package ru.sddisk.todorestapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sddisk.todorestapi.dto.TaskCreateRequest;
import ru.sddisk.todorestapi.dto.TaskResponse;
import ru.sddisk.todorestapi.dto.TaskUpdateRequest;
import ru.sddisk.todorestapi.mapper.TaskMapper;
import ru.sddisk.todorestapi.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks() {
        return new ResponseEntity<>(
                TaskMapper.toResponseList(
                        taskService.getTasks()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        return new ResponseEntity<>(
                TaskMapper.toResponse(
                        taskService.getTaskById(id)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest req) {
        return new ResponseEntity<>(
                TaskMapper.toResponse(
                        taskService.createTask(
                                TaskMapper.toEntity(req)
                        )
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody @Valid TaskUpdateRequest req) {
        return ResponseEntity.ok(
                TaskMapper.toResponse(
                        taskService.updateTask(
                                id,
                                TaskMapper.toEntity(req)
                        )
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}