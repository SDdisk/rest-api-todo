package ru.sddisk.todorestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sddisk.todorestapi.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}