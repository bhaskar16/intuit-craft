/*
package entities;

import java.util.List;
import java.util.logging.Logger;

import entities.comparators.NameComparator;
import entities.ordering.TaskOrderContext;
import entities.ordering.TopologicalOrdering;
import utility.TaskGenerationUtil;
import utility.exceptions.TaskOrderingException;

public class MainApp {
    public static void main(String[] args) {

        Logger LOGGER = Logger.getLogger(String.valueOf(MainApp.class));

        TaskGenerationUtil utility = new TaskGenerationUtil();
        List<Task> tasks = (List<Task>) utility.generateTasks();
        tasks.sort(new NameComparator());

        TaskOrderContext context = new TaskOrderContext();
        context.setOrderingStrategy(new TopologicalOrdering());
        try {
            List<Task> ordered = context.order(tasks);
            System.out.println(ordered);
        } catch (TaskOrderingException e) {
            throw new RuntimeException(e);
        }
    }
}
*/
