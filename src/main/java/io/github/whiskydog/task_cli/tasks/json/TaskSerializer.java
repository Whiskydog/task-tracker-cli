package io.github.whiskydog.task_cli.tasks.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.whiskydog.task_cli.tasks.Task;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TaskSerializer extends StdSerializer<Task> {
    private final DateTimeFormatter instantFormatter = DateTimeFormatter.ISO_INSTANT;

    public TaskSerializer() {
        super(Task.class);
    }

    @Override
    public void serialize(Task value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("description", value.getDescription());
        gen.writeStringField("status", value.getStatus().getValue());
        gen.writeStringField("createdAt", instantFormatter.format(value.getCreatedAt()));
        gen.writeStringField("updatedAt", instantFormatter.format(value.getUpdatedAt()));
        gen.writeEndObject();
    }
}
