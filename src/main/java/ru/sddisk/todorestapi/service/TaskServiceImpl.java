package ru.sddisk.todorestapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sddisk.todorestapi.dto.TaskDTO;
import ru.sddisk.todorestapi.dto.TaskFilterDTO;
import ru.sddisk.todorestapi.exception.TaskAlreadyExistException;
import ru.sddisk.todorestapi.exception.TaskNotFoundException;
import ru.sddisk.todorestapi.store.specification.TaskSpecs;
import ru.sddisk.todorestapi.store.entity.Task;
import ru.sddisk.todorestapi.store.repository.TaskRepository;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public List<TaskDTO> getTasks(TaskFilterDTO filter) {
        log.info("Fetching all tasks");

        Specification<Task> spec = Specification.where(null);

        if (filter.status() != null) {
            spec = spec.and(TaskSpecs.hasStatus(filter.status()));
            log.info("Filter by status:{}", filter.status());
        }

        List<Task> tasks = taskRepository.findAll(spec);

        log.debug("Fetched {} tasks", tasks.size());

        return tasks.stream()
                .map(TaskDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDTO getTaskById(Long taskId) {
        log.info("Fetching task with id:{}", taskId);
        Task fetchedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id:%s not found".formatted(taskId)));

        log.debug("Fetched task: {}", fetchedTask);

        return TaskDTO.fromEntity(fetchedTask);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDto) {
        log.info("Saving task with title: '{}'", taskDto.title());

        Task savedTask;
        try {
            savedTask = taskRepository.save(
                    new Task(
                            taskDto.title(),
                            taskDto.description(),
                            taskDto.status()
                    )
            );
        } catch (DataIntegrityViolationException ex) {
            // sh1t-ch3ck
            throw new TaskAlreadyExistException(
                    "Task with title:'%s' already exists".formatted(taskDto.title())
            );
        }

        log.debug("Saved task: {}", savedTask);

        return TaskDTO.fromEntity(savedTask);
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDto) {
        log.info("Update task with id:{}", taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id:%s not found".formatted(taskId)));

        log.debug("Task before: {}", task);

        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setStatus(taskDto.status());

        Task updatedTask = taskRepository.save(task);

        log.debug("Task after: {}", updatedTask);

        return TaskDTO.fromEntity(task);
    }

    @Override
    public void deleteTaskById(Long taskId) {
        log.info("Deleting task with id:{}", taskId);

        taskRepository.deleteById(taskId);

        log.debug("Successfully deleted task with id:{}", taskId);
    }

}
