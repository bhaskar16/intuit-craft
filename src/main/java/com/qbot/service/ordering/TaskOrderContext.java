package com.qbot.service.ordering;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qbot.entities.Task;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

/**
 * This serves as the Context class for the strategies of TaskOrdering.
 * Multiple strategies can be plugged into the ordering implementation, however, currently only th Topological ordering is provided.
 */
public class TaskOrderContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskOrderContext.class);

    private TaskOrderable orderable;

    public void setOrderingStrategy(TaskOrderable strategy) {
        this.orderable = strategy;
    }

    public List<Task> order(List<Task> unOrderedTasks) throws TaskOrderingException, NoTasksException {
        if (Objects.isNull(orderable)) {
            LOGGER.info("Seeding default ordering as no ordering strategy was set initially");
            setOrderingStrategy(new TopologicalOrderingImpl());
        }
        return orderable.orderTasks(unOrderedTasks);
    }

}
