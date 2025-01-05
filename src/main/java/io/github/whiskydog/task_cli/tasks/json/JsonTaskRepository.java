package io.github.whiskydog.task_cli.tasks.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.whiskydog.task_cli.tasks.Task;
import io.github.whiskydog.task_cli.tasks.TaskRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonTaskRepository implements TaskRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JavaType taskCollectionType = mapper.getTypeFactory().constructCollectionType(List.class, Task.class);
    private final Path path;

    public JsonTaskRepository(String fileName) {
        this.path = Paths.get(fileName);
        SimpleModule taskModule = new SimpleModule("TaskModule", new Version(1, 0, 0, null, null, null));
        taskModule.addSerializer(Task.class, new TaskSerializer());
        taskModule.addDeserializer(Task.class, new TaskDeserializer());
        mapper.registerModule(taskModule);
    }

    @Override
    public Task create(String taskDescription) {
        SortedMap<Integer, Task> tasks = readTasks();
        int nextId = tasks.isEmpty() ? 1 : tasks.lastKey() + 1;
        Task newTask = new Task(nextId, taskDescription);
        tasks.put(newTask.getId(), newTask);
        writeTasks(tasks);
        return newTask;
    }

    @Override
    public boolean update(Task task) {
        Map<Integer, Task> tasks = readTasks();
        if (tasks.put(task.getId(), task) != null) {
            writeTasks(tasks);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int taskId) {
        Map<Integer, Task> tasks = readTasks();
        Task removedTask = tasks.remove(taskId);
        if (removedTask != null) {
            writeTasks(tasks);
            return true;
        }
        return false;
    }

    @Override
    public Map<Integer, Task> findAll() {
        return readTasks();
    }

    @Override
    public Optional<Task> findById(int taskId) {
        Map<Integer, Task> tasks = readTasks();
        return Optional.ofNullable(tasks.get(taskId));
    }

    private SortedMap<Integer, Task> readTasks() {
        SortedMap<Integer, Task> tasks = new TreeMap<>();
        try (InputStream stream = Files.newInputStream(path)) {
            Collection<Task> taskCollection = mapper.readValue(stream, taskCollectionType);
            taskCollection.forEach(task -> tasks.put(task.getId(), task));
        } catch (NoSuchFileException e) {
            System.err.println("Task file not found, creating...");
            writeTasks(tasks);
        } catch (JacksonException e) {
            System.err.println("Failed to parse JSON");
        } catch (IOException e) {
            System.err.println("Failed to read tasks, I/O error. " + e.getMessage());
        }

        return tasks;
    }

    private void writeTasks(Map<Integer, Task> tasks) {
        try (OutputStream out = Files.newOutputStream(path)) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, tasks.values());
        } catch (IOException e) {
            System.err.println("Failed to write tasks, I/O error. " + e.getMessage());
        }
    }
}
