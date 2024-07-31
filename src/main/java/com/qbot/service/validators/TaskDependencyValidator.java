package com.qbot.service.validators;

import java.util.List;

import com.qbot.entities.Task;

/**
 * This interface validates dependencies within tasks and whether adding a new dependency will create a loop or not.
 *
 * The validDependency should return:
 *  - true if the dependency is allowed
 *  - false if the dependency is not allowed
 */
public interface TaskDependencyValidator extends Validator {
    boolean validDependency(List<Task> tasks, Task dependency);
}
