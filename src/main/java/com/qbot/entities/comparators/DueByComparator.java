package com.qbot.entities.comparators;

import java.time.Duration;
import java.util.Comparator;

import com.qbot.entities.Task;

public class DueByComparator implements Comparator<Task> {

    @Override
    public int compare(Task first, Task second) {
        return Math.toIntExact(Duration.between(first.getDueBy(), second.getDueBy())
          .toSeconds());
    }
}
