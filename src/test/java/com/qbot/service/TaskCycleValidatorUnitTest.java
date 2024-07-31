package com.qbot.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.qbot.entities.Task;
import com.qbot.service.validators.DepthFirstDependencyValidatorImpl;
import com.qbot.service.validators.TaskDependencyValidatorContext;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

public class TaskCycleValidatorUnitTest {

    private static TaskDependencyValidatorContext context = null;
    private static TestUtil testUtil;

    @BeforeAll
    public static void setup() {
        context = new TaskDependencyValidatorContext();
        context.setValidatorContext(new DepthFirstDependencyValidatorImpl());
        testUtil = new TestUtil();
    }

    @Test
    void givenTasksWithDependencies_whenAddingNewDependency_shouldReturnTrue() throws TaskOrderingException, NoTasksException {
        List<Task> myTasks = testUtil.generateTasksWithDefaultDependencies();
        Task newTask = new Task();
        newTask.setId(7);
        newTask.setDescription("T7");
        newTask.addDependency(myTasks.get(0));

        assertTrue(context.isValid(myTasks));
    }

    @Test
    void givenTasksWithDependencies_whenAddingCycleDependency_shouldReturnFalse() throws TaskOrderingException, NoTasksException {
        List<Task> myTasks = testUtil.generateTasksWithDefaultDependencies();

        Task firstTask = testUtil.findTaskById(myTasks, 5);
        firstTask.addDependency(testUtil.findTaskById(myTasks, 0));
        assertFalse(context.isValid(myTasks));
    }
}
