package io.github.whiskydog.task_cli;

import io.github.whiskydog.task_cli.tasks.json.JsonTaskRepository;
import io.github.whiskydog.task_cli.tasks.Task;
import io.github.whiskydog.task_cli.tasks.util.TaskPrinter;
import io.github.whiskydog.task_cli.tasks.TaskStatus;

import java.util.Collection;

public class Main {
    private static final Application app = new Application(new JsonTaskRepository("tasks.json"));
    private static final TaskPrinter printer = new TaskPrinter();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided.");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length > 1 && !args[1].isBlank()) {
                    handleAddTask(args[1]);
                } else {
                    System.out.println("Missing or invalid task description");
                }
                break;
            case "delete":
                if (args.length > 1 && !args[1].isBlank()) {
                    handleDeleteTask(args[1]);
                } else {
                    System.out.println("Missing or invalid task ID");
                }
                break;
            case "update":
                if (args.length > 2 && !args[1].isBlank() && !args[2].isBlank()) {
                    handleUpdateTask(args[1], args[2]);
                } else {
                    System.out.println("Missing or invalid task ID or description");
                }
                break;
            case "mark-in-progress":
                if (args.length > 1 && !args[1].isBlank()) {
                    handleMarkInProgress(args[1]);
                } else {
                    System.out.println("Missing or invalid task ID");
                }
                break;
            case "mark-done":
                if (args.length > 1 && !args[1].isBlank()) {
                    handleMarkDone(args[1]);
                } else {
                    System.out.println("Missing or invalid task ID");
                }
                break;
            case "list":
                if (args.length > 1 && !args[1].isBlank()) {
                    handleListTasksOfStatus(args[1]);
                } else {
                    handleListAllTasks();
                }
                break;
            default:
                System.out.printf("Unknown command: %s%n", args[0]);
        }

    }

    private static void handleAddTask(String taskDescription) {
        Task task = app.addTask(taskDescription);
        System.out.printf("Task added with ID %d%n", task.getId());
    }

    private static void handleDeleteTask(String taskId) {
        int id;
        try {
            id = Integer.parseInt(taskId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID");
            return;
        }

        if (app.deleteTask(id)) {
            System.out.printf("Task with ID %d deleted%n", id);
        } else {
            System.out.printf("Task with ID %d not found%n", id);
        }
    }

    private static void handleUpdateTask(String taskId, String newDescription) {
        int id;
        try {
            id = Integer.parseInt(taskId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID");
            return;
        }

        if (app.updateTask(id, newDescription)) {
            System.out.printf("Task with ID %d updated%n", id);
        } else {
            System.out.printf("Task with ID %d not found%n", id);
        }
    }

    private static void handleMarkInProgress(String taskId) {
        int id;
        try {
            id = Integer.parseInt(taskId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID");
            return;
        }

        if (app.markInProgress(id)) {
            System.out.printf("Task with ID %d marked as in progress%n", id);
        } else {
            System.out.printf("Task with ID %d not found%n", id);
        }
    }

    private static void handleMarkDone(String taskId) {
        int id;
        try {
            id = Integer.parseInt(taskId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID");
            return;
        }

        if (app.markDone(id)) {
            System.out.printf("Task with ID %d marked as done%n", id);
        } else {
            System.out.printf("Task with ID %d not found%n", id);
        }
    }

    private static void handleListTasksOfStatus(String status) {
        TaskStatus taskStatus;
        switch (status.toLowerCase()) {
            case "todo":
                taskStatus = TaskStatus.TODO;
                break;
            case "in-progress":
                taskStatus = TaskStatus.IN_PROGRESS;
                break;
            case "done":
                taskStatus = TaskStatus.DONE;
                break;
            default:
                System.out.printf("Unknown status: %s%n", status);
                return;
        }

        Collection<Task> tasks = app.getTasksWithStatus(taskStatus);
        if (tasks.isEmpty()) {
            System.out.printf("No tasks found with status %s%n", status);
        } else {
            printer.printTasksDetailed(tasks);
        }
    }

    private static void handleListAllTasks() {
        Collection<Task> tasks = app.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found");
        } else {
            printer.printTasksDetailed(tasks);
        }
    }
}