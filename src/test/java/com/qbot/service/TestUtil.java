package com.qbot.service;

import java.util.List;

import com.qbot.entities.Task;
import com.qbot.utility.TaskGenerationUtil;

public class TestUtil {
    Task findTaskById(List<Task> myTasks, int x) {
        return myTasks.stream()
          .filter(task -> task.getId() == x)
          .findFirst()
          .get();
    }

    List<Task> generateTasksWithDefaultDependencies() {
        TaskGenerationUtil util = new TaskGenerationUtil();
        return (List<Task>) util.generateTasks();
    }

    List<Task> generateTasksWithCyclicDependencies() {
        TaskGenerationUtil util = new TaskGenerationUtil();
        List<Task> tasks = (List<Task>) util.generateTasks();
        util.addCyclicDependencies(tasks);

        return tasks;
    }
}
