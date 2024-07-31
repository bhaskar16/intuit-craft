package com.qbot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qbot.entities.Task;
import com.qbot.service.ordering.TaskOrderContext;
import com.qbot.service.ordering.TopologicalOrdering;
import com.qbot.service.validators.TaskDependencyValidatorContext;
import com.qbot.utility.TaskGenerationUtil;
import com.qbot.utility.exceptions.TaskOrderingException;

@Service
public class TaskService {

    private final TaskGenerationUtil taskGenerationUtil = new TaskGenerationUtil();
    private final TaskOrderContext taskOrderContext = new TaskOrderContext();
    private final TaskDependencyValidatorContext validatorContext = new TaskDependencyValidatorContext();

    public List<Task> getDefaultTasks() {
        return (List<Task>) taskGenerationUtil.generateTasks();
    }

    public Optional<Task> getTaskById(Integer id) {
        return taskGenerationUtil.generateTasks()
          .stream()
          .filter(task -> task.getId() == id)
          .findFirst();
    }

    public boolean addDependency(Integer baseTask, Integer dependentTask) {
        Optional<Task> existingTask = getTaskById(baseTask);
        List<Task> allTasks = (List<Task>) taskGenerationUtil.generateTasks();
        Optional<Task> newTask = getTaskById(dependentTask);

        if (validatorContext.isValid(allTasks, newTask.get())) {
            existingTask.get()
              .addDependency(newTask.get());
            return true;
        }
        return false;
    }

    public List<Task> scheduleTasks() {
        List<Task> ordered;
        taskOrderContext.setOrderingStrategy(new TopologicalOrdering());
        try {
            ordered = taskOrderContext.order((List<Task>) taskGenerationUtil.generateTasks());
        } catch (TaskOrderingException e) {
            throw new RuntimeException(e);
        }
        return ordered;
    }
}
