package io.github.whiskydog.task_cli.tasks;

import java.util.Map;
import java.util.Optional;

public interface TaskRepository {
    Task create(String taskDescription);

    boolean update(Task task);

    boolean deleteById(int taskId);

    Map<Integer, Task> findAll();

    Optional<Task> findById(int taskId);
}
