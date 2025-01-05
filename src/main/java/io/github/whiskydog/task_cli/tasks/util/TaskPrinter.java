package io.github.whiskydog.task_cli.tasks.util;

import io.github.whiskydog.task_cli.tasks.Task;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class TaskPrinter {
    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public void printTasksDetailed(Collection<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("========================================");
            System.out.println("ID:           " + task.getId());
            System.out.println("Description:  " + task.getDescription());
            System.out.println("Status:       " + task.getStatus());
            System.out.println("Created:      " + formatInstant(task.getCreatedAt()));
            System.out.println("Updated:      " + formatInstant(task.getUpdatedAt()));
            System.out.println();
        }
    }

    private String formatInstant(Instant instant) {
        return dateFormatter.format(instant);
    }
}
