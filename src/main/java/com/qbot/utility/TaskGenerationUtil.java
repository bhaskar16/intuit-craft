package com.qbot.utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.qbot.entities.Task;

public class TaskGenerationUtil {
    public Collection<Task> generateTasks() {
        List<Task> tasks = new ArrayList<>();
        int i = 0;
        while(i < 6) {
            tasks.add(getTask(i++));
        }

        addDependencies1(tasks);
        return tasks;
    }

    private static Task getTask(int i) {
        Task t  = new Task();
        t.setId(i);
        t.setDescription("T" + (i));
        t.setDueBy(LocalDateTime.now());
        return t;
    }

    private void addDependencies1(List<Task> tasks) {
        tasks.get(2).addDependency(tasks.get(5));
        tasks.get(0).addDependency(tasks.get(5));
        tasks.get(1).addDependency(tasks.get(4));
        tasks.get(3).addDependency(tasks.get(2));
        tasks.get(1).addDependency(tasks.get(3));


        tasks.get(0).addDependency(tasks.get(1));
    }

    private void addDependencies2(List<Task> tasks) {
        tasks.get(5).addDependency(tasks.get(2));
        tasks.get(5).addDependency(tasks.get(0));
        tasks.get(4).addDependency(tasks.get(1));
        tasks.get(2).addDependency(tasks.get(3));
        tasks.get(3).addDependency(tasks.get(1));


        tasks.get(1).addDependency(tasks.get(0));
    }


}
