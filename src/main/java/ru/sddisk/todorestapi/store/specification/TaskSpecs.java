package ru.sddisk.todorestapi.store.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.sddisk.todorestapi.store.entity.Task;
import ru.sddisk.todorestapi.store.entity.enums.TaskStatus;

public class TaskSpecs {
    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, cb) -> status != null ? cb.equal(root.get("status"), status) : null;
    }
}
