package com.qbot.service.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qbot.entities.Task;

public class DepthFirstDependencyValidatorImpl implements TaskDependencyValidator {

    Map<Integer, List<Integer>> graph = new HashMap<>();

    int vertices = 0;
    boolean[] visited;
    boolean[] recursiveStack;

    @Override
    public boolean validDependency(final List<Task> tasks, Task dependency) {

        List<Task> updatedTasks = new ArrayList<>(tasks);
        updatedTasks.add(dependency);
        vertices = updatedTasks.size();

        generateGraph(updatedTasks);
        return !isCyclic();

    }

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

    private boolean isCyclic() {
        visited = new boolean[vertices];
        recursiveStack = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
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
