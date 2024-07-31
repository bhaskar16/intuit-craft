package com.qbot.service.scheduler;

import java.util.List;

import com.qbot.entities.Task;
import com.qbot.service.ordering.TaskOrderContext;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

/**
 * This interface is primarily used by the Scheduling system layer.
 * An implementation of the Schedulable expects an Ordering Context to be sent and the tasks which are to be scheduled.
 *
 * A completely new List of tasks ready for scheduling are returned back and the original list is not modified.
 */
public interface Schedulable {
    List<Task> scheduleTasks(TaskOrderContext orderContext, List<Task> myTasks) throws TaskOrderingException, NoTasksException;
}
