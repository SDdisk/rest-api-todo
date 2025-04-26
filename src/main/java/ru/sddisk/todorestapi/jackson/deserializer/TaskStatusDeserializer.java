package ru.sddisk.todorestapi.jackson.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sddisk.todorestapi.exception.InvalidTaskStatusException;
import ru.sddisk.todorestapi.store.entity.enums.TaskStatus;

import java.io.IOException;
import java.util.Arrays;

public class TaskStatusDeserializer extends StdDeserializer<TaskStatus> {
    private static final Logger log = LoggerFactory.getLogger(TaskStatusDeserializer.class);

    public TaskStatusDeserializer() {
        super(TaskStatus.class);
    }

    @Override
    public TaskStatus deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
    ) throws IOException, JacksonException {
        String value = jsonParser.getText().toUpperCase();
        try {
            return TaskStatus.valueOf(value);
        } catch (IllegalArgumentException ex) {
            log.error("JSON parse error | Message: {}", ex.getMessage());
            throw new InvalidTaskStatusException(
                    "Invalid status. Allowed values: " + Arrays.toString(TaskStatus.values())
            );
        }
    }
}