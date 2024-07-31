package com.qbot.service.ordering;

import java.util.List;

import com.qbot.entities.Task;
import com.qbot.utility.exceptions.TaskOrderingException;

public class TaskOrderContext {
    private TaskOrderable orderable;

    public void setOrderingStrategy(TaskOrderable strategy) {
        this.orderable = strategy;
    }

    public List<Task> order(List<Task> unOrderedTasks) throws TaskOrderingException {
        return orderable.orderTasks(unOrderedTasks);
    }

}
