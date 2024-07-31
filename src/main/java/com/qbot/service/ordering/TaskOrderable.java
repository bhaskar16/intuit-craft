package com.qbot.service.ordering;

import java.util.List;

import com.qbot.entities.Task;
import com.qbot.utility.exceptions.TaskOrderingException;

public interface TaskOrderable {
    List<Task> orderTasks(List<Task> unorderedTasks) throws TaskOrderingException;
}
