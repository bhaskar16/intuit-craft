package com.qbot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qbot.entities.Task;
import com.qbot.service.ordering.TaskOrderContext;
import com.qbot.service.ordering.TopologicalOrdering;
import com.qbot.utility.TaskGenerationUtil;
import com.qbot.utility.exceptions.TaskOrderingException;

@Service
public class TaskService {

    private final TaskGenerationUtil taskGenerationUtil = new TaskGenerationUtil();

    public List<Task> getDefaultTasks() {
        return (List<Task>) taskGenerationUtil.generateTasks();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskGenerationUtil.generateTasks()
          .stream()
          .filter(task -> task.getId() == id)
          .findFirst();
    }

    public List<Task> scheduleTasks() {
        TaskOrderContext context = new TaskOrderContext();
        List<Task> ordered;
        context.setOrderingStrategy(new TopologicalOrdering());
        try {
            ordered = context.order((List<Task>) taskGenerationUtil.generateTasks());
        } catch (TaskOrderingException e) {
            throw new RuntimeException(e);
        }
        return ordered;
    }
}
