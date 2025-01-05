package io.github.whiskydog.task_cli.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskStatusTest {

    @Test
    public void testFromString() {
        assertEquals(TaskStatus.TODO, TaskStatus.fromString("todo"));
        assertEquals(TaskStatus.IN_PROGRESS, TaskStatus.fromString("in-progress"));
        assertEquals(TaskStatus.DONE, TaskStatus.fromString("done"));
        assertThrows(IllegalArgumentException.class, () -> TaskStatus.fromString("invalid"));
    }

    @Test
    public void testGetValue() {
        assertEquals("todo", TaskStatus.TODO.getValue());
        assertEquals("in-progress", TaskStatus.IN_PROGRESS.getValue());
        assertEquals("done", TaskStatus.DONE.getValue());
    }

    @Test
    public void testToString() {
        assertEquals("todo", TaskStatus.TODO.toString());
        assertEquals("in-progress", TaskStatus.IN_PROGRESS.toString());
        assertEquals("done", TaskStatus.DONE.toString());
    }
}
