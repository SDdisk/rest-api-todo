package ru.sddisk.todorestapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sddisk.todorestapi.exception.TaskAlreadyExistsException;
import ru.sddisk.todorestapi.exception.TaskNotFoundException;
import ru.sddisk.todorestapi.model.Task;
import ru.sddisk.todorestapi.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /*
        GET ALL
        GET ONE BY ...
     */

    // get all
    @Transactional(readOnly = true)
    public List<Task> getTasks() {
        log.info("Fetching all tasks");
        List<Task> tasks = taskRepository.findAll();
        log.debug("Fetched {} tasks", tasks.size());
        return tasks;
    }

    // get one by id
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        log.info("Fetching task with id: {}", id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: {" + id + "} not found"));
    }

    /*
        CREATE
        UPDATE
        DELETE BY ...
     */

    // create
    @Transactional
    public Task createTask(Task task) {
        log.info("Creating task with title: {}", task.getTitle());
        if (taskRepository.findByTitle(task.getTitle()).isPresent()) {
            throw new TaskAlreadyExistsException("Task with title: {'" + task.getTitle() + "'} already exists");
        }

        return taskRepository.save(task);
    }

    // update
    @Transactional
    public Task updateTask(Long id, Task newTask) {
        log.info("Updating task with id: {}", id);
        Task toUpdate = getTaskById(id);

        log.debug("Task before: {}", toUpdate);

        if (newTask.getTitle() != null)
            toUpdate.setTitle(newTask.getTitle());

        if (newTask.getDescription() != null)
            toUpdate.setDescription(newTask.getDescription());

        log.debug("Task after: {}", toUpdate);

        return taskRepository.save(toUpdate);
    }

    // delete by id
    @Transactional
    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task with id: {" + id + "} not found");
        }

        taskRepository.deleteById(id);
    }
}
