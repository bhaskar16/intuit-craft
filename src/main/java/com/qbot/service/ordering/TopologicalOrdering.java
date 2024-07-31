package com.qbot.service.ordering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.qbot.entities.Task;
import com.qbot.utility.exceptions.TaskOrderingException;

public class TopologicalOrdering implements TaskOrderable {

    public List<Task> orderTasks(final List<Task> tasks) throws TaskOrderingException {
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
        if (tasksAccounted != tasks.size()) {
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
