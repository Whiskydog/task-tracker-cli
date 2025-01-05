# Task Tracker CLI

This is a simple CLI tool to track tasks. It allows you to add, update, delete, and list tasks. It also allows you to
mark tasks
as in progress and done. The tasks are stored in a JSON file in the current working directory.

This repository is a solution to the [Task Tracker project](https://roadmap.sh/projects/task-tracker)
on [roadmap.sh](https://roadmap.sh). **If you liked my solution, please [upvote it](https://roadmap.sh/projects/task-tracker/solutions?u=64b879d48a29ad56fa990e82).**

## Building

To build the CLI tool, run the following command:

```bash
gradlew build
```

This will create a `build/distributions` directory with the built CLI tool. Simply extract the contents of the archive
and run the `task-tracker` script or batch file inside the `bin` folder to use the CLI tool.

**Alternatively, you can download one of the releases from the [releases page](https://github.com/Whiskydog/task-tracker-cli/releases).**

## Testing

To run the unit tests, use the following command:

```bash
gradlew test
```

## Running

If you wish to run the tool without building it, you can use the following command:

```bash
gradlew run --args='command arguments'
```

For example:

```bash
gradlew run --args='add "Task description"'
```

## Usage

To add a task, use the following command:

```bash
task-cli add "Task description"
```

To update a task, use the following command:

```bash
task-cli update <task-id> "New task description"
```

To delete a task, use the following command:

```bash
task-cli delete <task-id>
```

To list all tasks, use the following command:

```bash
task-cli list
```

To list tasks by status, use the following commands:

```bash
task-cli list done
task-cli list todo
task-cli list in-progress
```

To mark a task as in progress, use the following command:

```bash
task-cli mark-in-progress <task-id>
```

To mark a task as done, use the following command:

```bash
task-cli mark-done <task-id>
```

## Notes

The Task Tracker project requirements on [roadmap.sh](https://roadmap.sh/projects/task-tracker) state the following
constraints:

- Use the native file system module of your programming language to interact with the JSON file.
- Do not use any external libraries or frameworks to build this project.

However, this solution being done in Java, and although the solution does make use of the built-in `java.nio.file`
package to interact with the file system,
I decided to utilize [Jackson](https://github.com/FasterXML/jackson-databind) to handle JSON serialization and
deserialization.
This is because the native file system module in Java is not as user-friendly as the Jackson library for handling JSON
files.
I hope this is acceptable for the project requirements, specially considering how common it is to use Jackson for JSON
serialization and deserialization in Java projects.
