package com.qbot.service.ordering;

import java.util.List;

import com.qbot.entities.Task;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

/**
 * This interface defines the functionality required to Order *Task* objects based on an ordering logic.
 * The orderTasks returns a new List of Tasks based on the underlying ordering logic.
 */
public interface TaskOrderable {
    List<Task> orderTasks(final List<Task> unorderedTasks) throws TaskOrderingException, NoTasksException;
}
