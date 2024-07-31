package com.qbot.service.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qbot.entities.Task;
import com.qbot.service.ordering.TaskOrderContext;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

/**
 * This is a specific class of Scheduling algorithm which relies on ordering and scheduling tasks based
 * on the inherent dependencies among the Tasks.
 *
 * Independent Tasks are scheduled before Dependant ones.
 */
public class DependencyOrdererScheduler implements Schedulable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyOrdererScheduler.class);

    /**
     * The TaskOrderContext is to be sent alongside the original list of tasks.
     * This throws TaskOrderingException, NoTasksException.
     * @param orderContext
     * @param myTasks
     * @return
     * @throws TaskOrderingException
     * @throws NoTasksException
     */
    @Override
    public List<Task> scheduleTasks(TaskOrderContext orderContext, final List<Task> myTasks) throws TaskOrderingException, NoTasksException {
        LOGGER.info("Generating Schedule of Tasks");
        LOGGER.info("Ordering Context received {} ", orderContext.getClass());
        List<Task> tasksToSchedule = new ArrayList<>(myTasks);
        return orderContext.order(tasksToSchedule);
    }
}
