package io.github.whiskydog.task_cli.tasks.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import io.github.whiskydog.task_cli.tasks.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskSerializerTest {
    @Test
    public void testSerialize() throws IOException {
        Task task = new Task(1, "Test Task", "todo", "2021-01-01T00:00:00Z", "2021-01-01T00:00:00Z");
        TaskSerializer serializer = new TaskSerializer();
        StringWriter writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);
        serializer.serialize(task, generator, null);
        generator.close();
        String json = writer.toString();
        assertEquals("{\"id\":1,\"description\":\"Test Task\",\"status\":\"todo\",\"createdAt\":\"2021-01-01T00:00:00Z\",\"updatedAt\":\"2021-01-01T00:00:00Z\"}", json);
    }
}
