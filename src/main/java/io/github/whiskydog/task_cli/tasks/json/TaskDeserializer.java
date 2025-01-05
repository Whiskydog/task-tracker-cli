package io.github.whiskydog.task_cli.tasks.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.github.whiskydog.task_cli.tasks.Task;

import java.io.IOException;

public class TaskDeserializer extends StdDeserializer<Task> {

    public TaskDeserializer() {
        this(null);
    }

    public TaskDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        JsonNode idNode = node.get("id");
        JsonNode descriptionNode = node.get("description");
        JsonNode statusNode = node.get("status");
        JsonNode createdAtNode = node.get("createdAt");
        JsonNode updatedAtNode = node.get("updatedAt");

        int id = idNode.asInt();
        String description = descriptionNode.asText();
        String status = statusNode.asText();
        String createdAt = createdAtNode.asText();
        String updatedAt = updatedAtNode.asText();

        return new Task(id, description, status, createdAt, updatedAt);
    }


}
