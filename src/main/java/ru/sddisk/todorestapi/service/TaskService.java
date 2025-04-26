package ru.sddisk.todorestapi.service;

import ru.sddisk.todorestapi.dto.TaskDTO;
import ru.sddisk.todorestapi.dto.TaskFilterDTO;
import ru.sddisk.todorestapi.store.entity.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasks(TaskFilterDTO filter);
    TaskDTO getTaskById(Long taskId);

    TaskDTO createTask(TaskDTO taskDto);
    TaskDTO updateTask(Long taskId, TaskDTO taskDto);
    void deleteTaskById(Long taskId);
}
