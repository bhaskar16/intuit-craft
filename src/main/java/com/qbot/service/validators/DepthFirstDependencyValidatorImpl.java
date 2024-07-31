package com.qbot.service.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qbot.entities.Task;

public class DepthFirstDependencyValidatorImpl implements TaskDependencyValidator {

    Map<Integer, List<Integer>> graph = new HashMap<>();
    public static final int TOTAL_VERTICES_UPPER_BOUND = 1_000;
    private static final Logger LOGGER = LoggerFactory.getLogger(DepthFirstDependencyValidatorImpl.class);

    int vertices = 0;
    boolean[] visited;
    boolean[] recursiveStack;

    @Override
    public boolean validDependency(final List<Task> tasks, Task dependency) {
        LOGGER.info("New Dependency evaluation phase {}", dependency.getDescription());

        List<Task> updatedTasks = new ArrayList<>(tasks);
        updatedTasks.add(dependency);

        vertices = updatedTasks.size();
        LOGGER.info("Total Number of Vertices {}", vertices);

        generateGraph(updatedTasks);
        return !isCyclic();

    }

    /**
     * Tries to generate a DAG with Adjacency list(in Map)
     * @param tasks list
     */
    private void generateGraph(final List<Task> tasks) {
        for (Task task : tasks) {
            graph.put(task.getId(), new ArrayList<>());
        }

        for (Task task : tasks) {
            for (Task existingDependent : task.getDependencies()) {
                graph.get(existingDependent.getId())
                  .add(task.getId());
            }
        }
    }

    /**
     * Performs Depth First Search with a visited array and another array
     * to maintain the stack recursive calls.
     * @return true/false
     */

    private boolean isCyclic() {
        visited = new boolean[TOTAL_VERTICES_UPPER_BOUND];
        recursiveStack = new boolean[TOTAL_VERTICES_UPPER_BOUND];
        for (int i : graph.keySet()) {
            if (isCyclicUtility(i, visited, recursiveStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCyclicUtility(int index, boolean[] visited, boolean[] recursiveStack) {
        if (recursiveStack[index]) {
            return true;
        }

        if (visited[index]) {
            return false;
        }

        visited[index] = true;
        recursiveStack[index] = true;

        List<Integer> associated = graph.get(index);

        for (Integer c : associated)
            if (isCyclicUtility(c, visited, recursiveStack))
                return true;

        recursiveStack[index] = false;
        return false;

    }
}
