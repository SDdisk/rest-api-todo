package ru.sddisk.todorestapi.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks() {
        log.info("GET /tasks");
        return new ResponseEntity<>(
                TaskMapper.toResponseList(
                        taskService.getTasks()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        log.info("GET /tasks/{} ", id);
        return new ResponseEntity<>(
                TaskMapper.toResponse(
                        taskService.getTaskById(id)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest req) {
        log.info("POST /tasks | Request: {}", req);
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
        log.info("PUT /tasks/{} | Request: {}", id, req);
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
        log.info("DELETE /tasks/{}", id);
        taskService.deleteTask(id);
        return new ResponseEntity<>(
                HttpStatus.NO_CONTENT
        );
    }
}