package ru.sddisk.todorestapi.service;

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
        return taskRepository.findAll();
    }

    // get one by id
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
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
        if (taskRepository.findByTitle(task.getTitle()).isPresent()) {
            throw new TaskAlreadyExistsException("Task with title: {'" + task.getTitle() + "'} already exists");
        }

        return taskRepository.save(task);
    }

    // update
    @Transactional
    public Task updateTask(Long id, Task newTask) {
        Task toUpdate = getTaskById(id);

        if (newTask.getTitle() != null)
            toUpdate.setTitle(newTask.getTitle());

        if (newTask.getDescription() != null)
            toUpdate.setDescription(newTask.getDescription());

        return taskRepository.save(toUpdate);
    }

    // delete by id
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
