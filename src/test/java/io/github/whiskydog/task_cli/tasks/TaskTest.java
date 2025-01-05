package io.github.whiskydog.task_cli.tasks;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testConstructor() {
        Task task = new Task(1, "Test Task");
        assertEquals(1, task.getId());
        assertEquals("Test Task", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertEquals(0, task.getUpdatedAt().compareTo(task.getCreatedAt()), "Created time and updated time should be the same");
    }

    @Test
    public void testCompleteConstructor() {
        Task task = new Task(1, "Test Task", "in-progress", "2021-01-01T00:00:00Z", "2021-01-01T00:01:00Z");
        assertEquals(1, task.getId());
        assertEquals("Test Task", task.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
        assertEquals("2021-01-01T00:00:00Z", task.getCreatedAt().toString());
        assertEquals("2021-01-01T00:01:00Z", task.getUpdatedAt().toString());
    }

    @Test
    public void testUpdate() {
        Clock pastClock = Clock.offset(Clock.systemDefaultZone(), Duration.ofMinutes(-1));
        Task task = new Task(1, "Test Task", pastClock);
        task.update("Updated Task");
        assertEquals("Updated Task", task.getDescription(), "Description should be updated");
        assertTrue(task.getUpdatedAt().isAfter(task.getCreatedAt()), "Updated time should be after created time");
    }

    @Test
    public void testSetStatus() {
        Task task = new Task(1, "Test Task");
        task.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus(), "Status should be updated");
    }
}
