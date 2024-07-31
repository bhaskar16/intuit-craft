package com.qbot.service.validators;

import java.util.List;

import com.qbot.entities.Task;

public interface TaskDependencyValidator extends Validator {
    boolean validDependency(List<Task> tasks, Task dependency);
}
