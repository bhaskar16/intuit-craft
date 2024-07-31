package com.qbot.service.validators;

import java.util.List;

import com.qbot.entities.Task;

public class TaskDependencyValidatorContext {
    private TaskDependencyValidator validatorContext;

    public void setValidatorContext(TaskDependencyValidator context) {
        this.validatorContext = context;
    }

    public boolean isValid(List<Task> existingTasks, Task newTask) {
        return this.validatorContext.validDependency(existingTasks, newTask);
    }
}
