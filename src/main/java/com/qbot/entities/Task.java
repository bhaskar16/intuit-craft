package com.qbot.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Task implements Comparable<Task>{

    private long id;

    private String description;

    private Collection<Task> dependencies;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private LocalDateTime dueBy;

    private Activity activity;

    public Task() {
        this.dependencies = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueBy() {
        return dueBy;
    }

    public void setDueBy(LocalDateTime dueBy) {
        this.dueBy = dueBy;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Collection<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Collection<Task> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(Task dependency) {
        if (Objects.isNull(this.dependencies)) {
            this.dependencies = new ArrayList<>();
        }
        this.dependencies.add(dependency);
    }

    @Override
    public String toString() {
//        return "Task{" + "description='" + description + '\'' + ", dependencies=" + dependencies + '}';
        return "Task " + id;
    }

    @Override
    public int compareTo(Task o) {
        return (int) (this.id - o.id);
    }
}
