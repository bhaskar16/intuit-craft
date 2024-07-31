package com.qbot.service.ordering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qbot.entities.Task;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

/**
 * This is a specific Task ordering implementation that performs Topological Ordering of the Tasks.
 * This method throws NoTasksException when there are no tasks to order.
 * This method throws TaskOrderingException when the algorithm is unable to order the tasks(possibly due to the presence of a cycle).
 */
public class TopologicalOrderingImpl implements TaskOrderable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopologicalOrderingImpl.class);

    /**
     * Returns a completely new list with the new order of Tasks.
     *
     * Steps :
     *  - Start
     *  - Generates the prerequisites for each task
     *  - Understands which tasks have no pre-requisite (therefore, in-degree = 0 in the context of a DAG)
     *  - Performs ordering starting with those nodes and gradually decreases the in-degrees of other adjacent nodes
     *  - Returns final order
     *  - End
     * @param tasks
     * @return
     * @throws TaskOrderingException
     * @throws NoTasksException
     */
    public List<Task> orderTasks(final List<Task> tasks) throws TaskOrderingException, NoTasksException {

        if (Objects.isNull(tasks)) {
            LOGGER.error("No tasks were present");
            throw new NoTasksException("No tasks were present. Ordering activity cancelled");
        }

        Map<Task, List<Task>> preRequisites = generatePrerequisites(tasks);
        Map<Task, Integer> indegrees = generateIndegrees(tasks);

        int tasksAccounted = 0;

        List<Task> orderedTasks = new ArrayList<>();

        Queue<Task> independentTasks = seedIndependantTasksFromIndegrees(indegrees, tasks);

        while (!independentTasks.isEmpty()) {
            Task top = independentTasks.poll();
            orderedTasks.add(top);
            tasksAccounted += 1;
            for (Task dependent : preRequisites.get(top)) {
                indegrees.put(dependent, indegrees.get(dependent) - 1);
                if (indegrees.get(dependent) == 0) {
                    independentTasks.offer(dependent);
                }
            }
        }

        LOGGER.info("Seeded Independent Tasks : {}", independentTasks);

        if (tasksAccounted != tasks.size()) {
            LOGGER.error("Cycle detected!!");
            throw new TaskOrderingException("It is not possible to order all the tasks based on the individual dependencies");
        }
        return orderedTasks;
    }

    private Map<Task, List<Task>> generatePrerequisites(List<Task> tasks) {
        Map<Task, List<Task>> preRequisites = new HashMap<>();
        for (Task task : tasks) {
            preRequisites.put(task, new ArrayList<>());
        }

        for (Task task : tasks) {
            for (Task dep : task.getDependencies()) {
                preRequisites.get(dep)
                  .add(task);
            }
        }
        return preRequisites;
    }

    private Map<Task, Integer> generateIndegrees(List<Task> tasks) {
        Map<Task, Integer> indegrees = new HashMap<>();
        for (Task t : tasks) {
            indegrees.put(t, 0);
        }
        for (Task task : tasks) {
            List<Task> dependents = (List<Task>) task.getDependencies();
            if (!dependents.isEmpty()) {
                indegrees.put(task, indegrees.get(task) + 1);
            }
        }
        return indegrees;
    }

    private Queue<Task> seedIndependantTasksFromIndegrees(Map<Task, Integer> indegrees, List<Task> tasks) {
        Queue<Task> independents = new LinkedList<>();
        for (Task task : tasks) {
            if (indegrees.get(task) == 0) {
                independents.offer(task);
            }
        }
        return independents;
    }
}
