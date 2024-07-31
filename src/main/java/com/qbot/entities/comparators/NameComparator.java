package com.qbot.entities.comparators;

import java.util.Comparator;

import com.qbot.entities.Task;

public class NameComparator implements Comparator<Task> {
    @Override
    public int compare(Task first, Task second) {
        return first.getDescription()
          .compareTo(second.getDescription());
    }
}
