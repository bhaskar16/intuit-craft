package com.qbot.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qbot.dto.TaskDTO;
import com.qbot.entities.Task;
import com.qbot.service.ordering.TaskOrderContext;
import com.qbot.service.scheduler.DependencyOrdererScheduler;
import com.qbot.service.scheduler.Schedulable;
import com.qbot.service.validators.DepthFirstDependencyValidatorImpl;
import com.qbot.service.validators.TaskDependencyValidatorContext;
import com.qbot.utility.TaskGenerationUtil;
import com.qbot.utility.exceptions.NoSuchTaskException;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

@Service
public class TaskService {

    private final TaskGenerationUtil taskGenerationUtil = new TaskGenerationUtil();
    private final TaskOrderContext taskOrderContext = new TaskOrderContext();
    private final TaskDependencyValidatorContext validatorContext = new TaskDependencyValidatorContext();
    private final Schedulable schedulable = new DependencyOrdererScheduler();

    private List<Task> myTasks = new ArrayList<>();

    public Task createNewTask(TaskDTO dto) {
        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setDueBy(dto.getDueDate());
        myTasks.add(task);
        return task;
    }

    public List<Task> getMyInMemoryTasks() {
        return Collections.unmodifiableList(myTasks);
    }

    public List<Task> getDefaultTasks() {
        return (List<Task>) taskGenerationUtil.generateTasks();
    }

    public Optional<Task> getTaskById(List<Task> tasks, Integer id) {
        return tasks.stream()
          .filter(task -> task.getId() == id)
          .findFirst();
    }

    public boolean addDependency(Integer baseTask, Integer dependentTask) throws NoSuchTaskException {

        validatorContext.setValidatorContext(new DepthFirstDependencyValidatorImpl());

        Optional<Task> existingTask = getTaskById(myTasks, baseTask);
        Optional<Task> newTask = getTaskById(myTasks, dependentTask);
        if (existingTask.isEmpty() || newTask.isEmpty()) {
            throw new NoSuchTaskException("No Task found, please check the ids");
        }
        if (existingTask.get()
          .getDependencies()
          .contains(newTask.get())) {
            return true;
        }
        existingTask.get()
          .addDependency(newTask.get());

        if (validatorContext.isValid(myTasks)) {
            return true;
        }
        existingTask.get()
          .removeDependency(newTask.get());
        return false;
    }

    public List<Task> scheduleTasks() throws TaskOrderingException, NoTasksException {

        return schedulable.scheduleTasks(taskOrderContext, myTasks);
    }
}
