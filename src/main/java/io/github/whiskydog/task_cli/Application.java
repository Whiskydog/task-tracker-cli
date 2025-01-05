package io.github.whiskydog.task_cli;

import io.github.whiskydog.task_cli.tasks.Task;
import io.github.whiskydog.task_cli.tasks.TaskRepository;
import io.github.whiskydog.task_cli.tasks.TaskStatus;

import java.util.Collection;
import java.util.Optional;

public class Application {
    private final TaskRepository repository;

    public Application(TaskRepository repository) {
        this.repository = repository;
    }

    public Task addTask(String description) {
        return repository.create(description);
    }

    public boolean updateTask(int taskId, String newDescription) {
        Optional<Task> taskOptional = repository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.update(newDescription);
            return repository.update(task);
        }
        return false;
    }

    public boolean deleteTask(int taskId) {
        return repository.deleteById(taskId);
    }

    public Collection<Task> getTasks() {
        return repository.findAll().values();
    }

    public Collection<Task> getTasksWithStatus(TaskStatus status) {
        return repository.findAll().values().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public boolean markInProgress(int taskId) {
        Optional<Task> taskOptional = repository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(TaskStatus.IN_PROGRESS);
            return repository.update(task);
        }
        return false;
    }

    public boolean markDone(int taskId) {
        Optional<Task> taskOptional = repository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(TaskStatus.DONE);
            return repository.update(task);
        }
        return false;
    }
}