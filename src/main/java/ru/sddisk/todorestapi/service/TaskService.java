package ru.sddisk.todorestapi.service;

import ru.sddisk.todorestapi.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasks();
    TaskDTO getTaskById(Long taskId);

    TaskDTO createTask(TaskDTO taskDto);
    TaskDTO updateTask(Long taskId, TaskDTO taskDto);
    TaskDTO deleteTaskById(Long taskId);
}
