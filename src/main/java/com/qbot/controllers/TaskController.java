package com.qbot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qbot.dto.TaskDTO;
import com.qbot.entities.Task;
import com.qbot.service.TaskService;
import com.qbot.utility.exceptions.NoSuchTaskException;
import com.qbot.utility.exceptions.NoTasksException;
import com.qbot.utility.exceptions.TaskOrderingException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getMyInMemoryTasks()
          .stream()
          .map(this::convertTaskToDTO)
          .toList();
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO task) {
        Task createdTask = taskService.createNewTask(task);
        return new ResponseEntity<>(convertTaskToDTO(createdTask), HttpStatus.CREATED);
    }

    @GetMapping(path = "/schedule")
    public ResponseEntity<List<TaskDTO>> schedule() {
        List<TaskDTO> scheduledTasks = null;
        try {
            scheduledTasks = taskService.scheduleTasks()
              .stream()
              .map(this::convertTaskToDTO)
              .toList();
        } catch (TaskOrderingException | NoTasksException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(scheduledTasks, HttpStatus.OK);
    }

    @PostMapping("/{existingTaskId}/dependencies/{newTaskId}")
    public ResponseEntity addDependency(@PathVariable Integer existingTaskId, @PathVariable Integer newTaskId) {
        boolean possibleToAdd = false;
        try {
            possibleToAdd = taskService.addDependency(existingTaskId, newTaskId);
        } catch (NoSuchTaskException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return possibleToAdd ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    private TaskDTO convertTaskToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueBy());
        taskDTO.setId(task.getId());
        for (Task dep : task.getDependencies()) {
            taskDTO.getDependecies()
              .add(dep.getId());
        }
        return taskDTO;
    }
}
