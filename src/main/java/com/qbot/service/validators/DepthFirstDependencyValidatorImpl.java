package com.qbot.service.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qbot.entities.Task;

public class DepthFirstDependencyValidatorImpl implements TaskDependencyValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepthFirstDependencyValidatorImpl.class);

    /**
     * Tries to generate a DAG with Adjacency list(in Map). Performs Topological Sort using Kahns Algorithm
     * @param tasks list
     */

    @Override
    public boolean validDependency(final List<Task> tasks) {
        Map<Task, List<Task>> preRequisites = generatePrerequisites(tasks);
        Map<Task, Integer> indegrees = generateIndegrees(tasks);

        int tasksAccounted = 0;

        Queue<Task> independentTasks = seedIndependantTasksFromIndegrees(indegrees, tasks);

        while (!independentTasks.isEmpty()) {
            Task top = independentTasks.poll();
            tasksAccounted += 1;
            for (Task dependent : preRequisites.get(top)) {
                indegrees.put(dependent, indegrees.get(dependent) - 1);
                if (indegrees.get(dependent) == 0) {
                    independentTasks.offer(dependent);
                }
            }
        }

        if (tasksAccounted != tasks.size()) {
            LOGGER.error("Cycle detected!!");
            return false;
        }
        return true;
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
