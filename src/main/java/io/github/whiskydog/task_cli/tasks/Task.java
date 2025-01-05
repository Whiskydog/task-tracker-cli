package io.github.whiskydog.task_cli.tasks;

import java.time.Clock;
import java.time.Instant;

public class Task {
    private final int id;
    private final Instant createdAt;
    private String description;
    private TaskStatus status;
    private Instant updatedAt;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Task(int id, String description, Clock clock) {
        this.id = id;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = Instant.now(clock);
        this.updatedAt = Instant.now(clock);
    }

    public Task(int id, String description, String status, String isoCreatedAt, String isoUpdatedAt) {
        this.id = id;
        this.description = description;
        this.status = TaskStatus.fromString(status);
        this.createdAt = Instant.parse(isoCreatedAt);
        this.updatedAt = Instant.parse(isoUpdatedAt);
    }

    public void update(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public int getId() {
        return id;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
