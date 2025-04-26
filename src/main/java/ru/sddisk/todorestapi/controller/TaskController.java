package ru.sddisk.todorestapi.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sddisk.todorestapi.dto.TaskDTO;
import ru.sddisk.todorestapi.dto.TaskFilterDTO;
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
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> getTasks(TaskFilterDTO filter) {
        log.info("GET /tasks");
        return taskService.getTasks(filter);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO getTask(@PathVariable Long taskId) {
        log.info("GET /tasks/{}", taskId);
        return taskService.getTaskById(taskId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDto) {
        log.info("POST /tasks | Request: {}", taskDto);
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskDTO taskDto) {
        log.info("PUT /tasks/{} | Request: {}", taskId, taskDto);
        return taskService.updateTask(taskId, taskDto);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable Long taskId) {
        log.info("DELETE /tasks/{}", taskId);
        taskService.deleteTaskById(taskId);
    }
}