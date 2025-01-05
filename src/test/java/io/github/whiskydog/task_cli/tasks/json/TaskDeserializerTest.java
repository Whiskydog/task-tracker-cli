package io.github.whiskydog.task_cli.tasks.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.whiskydog.task_cli.tasks.Task;
import io.github.whiskydog.task_cli.tasks.TaskStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDeserializerTest {
    @Test
    public void testDeserialize() throws IOException {
        String json = "{\"id\":1,\"description\":\"Test Task\",\"status\":\"todo\",\"createdAt\":\"2021-01-01T00:00:00Z\",\"updatedAt\":\"2021-01-01T00:00:00Z\"}";
        TaskDeserializer deserializer = new TaskDeserializer();
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        JsonParser parser = factory.createParser(json);
        parser.setCodec(mapper);
        Task task = deserializer.deserialize(parser, null);
        assertEquals(1, task.getId());
        assertEquals("Test Task", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(Instant.parse("2021-01-01T00:00:00Z"), task.getCreatedAt());
        assertEquals(Instant.parse("2021-01-01T00:00:00Z"), task.getUpdatedAt());
    }
}
