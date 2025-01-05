package io.github.whiskydog.task_cli.tasks;

public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public static TaskStatus fromString(String name) {
        return switch (name) {
            case "todo" -> TODO;
            case "in-progress" -> IN_PROGRESS;
            case "done" -> DONE;
            default -> throw new IllegalArgumentException("Invalid TaskStatus: " + name);
        };
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
