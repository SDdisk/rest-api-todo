package ru.sddisk.todorestapi.store.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.sddisk.todorestapi.jackson.deserializer.TaskStatusDeserializer;

@JsonDeserialize(using = TaskStatusDeserializer.class)
public enum TaskStatus {
    NEW,
    IN_PROGRESS,
    DONE,
    EXPIRED,
    ARCHIVED;

    @JsonCreator
    public static TaskStatus fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}
