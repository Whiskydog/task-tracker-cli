package io.github.whiskydog.task_cli;

import io.github.whiskydog.task_cli.tasks.Task;
import io.github.whiskydog.task_cli.tasks.TaskRepository;
import io.github.whiskydog.task_cli.tasks.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    private AutoCloseable closeable;

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private Application app;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testAddTask() {
        Task task = new Task(1, "Test task");
        when(repository.create("Test task")).thenReturn(task);

        Task result = app.addTask("Test task");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test task", result.getDescription());
        verify(repository, times(1)).create("Test task");
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task(1, "Test Task");
        when(repository.findById(1)).thenReturn(Optional.of(task));
        when(repository.update(task)).thenReturn(true);

        boolean result = app.updateTask(1, "Updated Task");

        assertTrue(result);
        assertEquals("Updated Task", task.getDescription());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).update(task);
    }

    @Test
    public void testUpdateTaskNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        boolean result = app.updateTask(1, "Updated Task");

        assertFalse(result);
        verify(repository, times(1)).findById(1);
        verify(repository, never()).update(any());
    }

    @Test
    public void testDeleteTask() {
        when(repository.deleteById(1)).thenReturn(true);

        boolean result = app.deleteTask(1);

        assertTrue(result);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void testGetTasks() {
        app.getTasks();
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetTasksWithStatus() {
        Task task1 = new Task(1, "Test Task 1");
        Task task2 = new Task(2, "Test Task 2");
        task1.setStatus(TaskStatus.IN_PROGRESS);

        when(repository.findAll()).thenReturn(Map.of(1, task1, 2, task2));

        Collection<Task> tasksInProgress = app.getTasksWithStatus(TaskStatus.IN_PROGRESS);
        assertTrue(tasksInProgress.contains(task1));
        assertFalse(tasksInProgress.contains(task2));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testMarkInProgress() {
        Task task = new Task(1, "Test Task");
        when(repository.findById(1)).thenReturn(Optional.of(task));
        when(repository.update(task)).thenReturn(true);

        boolean result = app.markInProgress(1);

        assertTrue(result);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).update(task);
    }

    @Test
    public void testMarkInProgressNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        boolean result = app.markInProgress(1);

        assertFalse(result);
        verify(repository, times(1)).findById(1);
        verify(repository, never()).update(any());
    }

    @Test
    public void testMarkDone() {
        Task task = new Task(1, "Test Task");
        when(repository.findById(1)).thenReturn(Optional.of(task));
        when(repository.update(task)).thenReturn(true);

        boolean result = app.markDone(1);

        assertTrue(result);
        assertEquals(TaskStatus.DONE, task.getStatus());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).update(task);
    }

    @Test
    public void testMarkDoneNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        boolean result = app.markDone(1);

        assertFalse(result);
        verify(repository, times(1)).findById(1);
        verify(repository, never()).update(any());
    }
}
