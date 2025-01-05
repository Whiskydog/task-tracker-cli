package io.github.whiskydog.task_cli.tasks.json;

import io.github.whiskydog.task_cli.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTaskRepositoryTest {

    @TempDir
    Path tempDir;

    private JsonTaskRepository repository;

    @BeforeEach
    public void setUp() {
        Path tempFile = tempDir.resolve("tasks.json");
        repository = new JsonTaskRepository(tempFile.toString());
    }

    @Test
    public void testCreateTask() {
        Task task = repository.create("Test Task");
        assertNotNull(task);
        assertEquals("Test Task", task.getDescription());
        assertTrue(repository.findById(task.getId()).isPresent());
    }

    @Test
    public void testUpdateTask() {
        Task task = repository.create("Initial Task");
        task.update("Updated Task");
        assertTrue(repository.update(task));
        Optional<Task> updatedTask = repository.findById(task.getId());
        assertTrue(updatedTask.isPresent());
        assertEquals("Updated Task", updatedTask.get().getDescription());
    }

    @Test
    public void testUpdateTaskNotFound() {
        Task task = new Task(1, "Task not in repository");
        assertFalse(repository.update(task));
    }

    @Test
    public void testDeleteTask() {
        Task task = repository.create("Task to be deleted");
        assertTrue(repository.deleteById(task.getId()));
        assertFalse(repository.findById(task.getId()).isPresent());
    }

    @Test
    public void testDeleteTaskNotFound() {
        assertFalse(repository.deleteById(1));
    }

    @Test
    public void testFindAllTasks() {
        repository.create("Task 1");
        repository.create("Task 2");
        assertEquals(2, repository.findAll().size());
    }

    @Test
    public void testFindAllTasksEmpty() {
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void testFindById() {
        Task task = repository.create("Find me");
        Optional<Task> foundTask = repository.findById(task.getId());
        assertTrue(foundTask.isPresent());
        assertEquals("Find me", foundTask.get().getDescription());
    }
}