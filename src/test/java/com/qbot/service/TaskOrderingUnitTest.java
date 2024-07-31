package com.qbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.qbot.entities.Task;
import com.qbot.service.ordering.TaskOrderContext;
import com.qbot.service.ordering.TopologicalOrderingImpl;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

public class TaskOrderingUnitTest {

    private static TaskOrderContext context = null;
    private static TestUtil testUtil;

    @BeforeAll
    public static void setup() {
        context = new TaskOrderContext();
        context.setOrderingStrategy(new TopologicalOrderingImpl());
        testUtil = new TestUtil();
    }

    @Test
    void givenTasksWithDependencies_whenOrdered_shouldReturnCorrectOrderOfTasks() throws TaskOrderingException, NoTasksException {
        List<Task> myTasks = testUtil.generateTasksWithDefaultDependencies();

        List<Task> orderedTask = context.order(myTasks);
        assertEquals(orderedTask.size(), myTasks.size());
        assertNotEquals(myTasks.get(0), orderedTask.get(0));

        Task task4 = testUtil.findTaskById(myTasks, 4);
        Task task1 = testUtil.findTaskById(myTasks, 1);
        Task task2 = testUtil.findTaskById(myTasks, 2);

        assertTrue(orderedTask.indexOf(task4) < orderedTask.indexOf(task1));
        assertTrue(orderedTask.indexOf(task4) < orderedTask.indexOf(task2));
    }

    @Test
    void givenTasksWithCyclicDependencies_whenOrdered_shouldReturnError() throws TaskOrderingException, NoTasksException {
        List<Task> myTasks = testUtil.generateTasksWithCyclicDependencies();

        assertThrows(TaskOrderingException.class, () -> context.order(myTasks));
    }

    @Test
    void givenNoTasks_whenOrdered_shouldReturnCorrectError() throws TaskOrderingException, NoTasksException {

        assertThrows(NoTasksException.class, () -> context.order(null));
    }
}
