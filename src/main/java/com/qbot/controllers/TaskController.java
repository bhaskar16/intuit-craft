package com.qbot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qbot.dto.TaskDTO;
import com.qbot.entities.Task;
import com.qbot.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getDefaultTasks();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping(path = "/schedule")
    public List<TaskDTO> schedule() {
        return taskService.scheduleTasks()
          .stream()
          .map(this::convertTaskToDTO)
          .toList();
    }

    @PutMapping(path = "/dependency")
    public boolean addDependency(Integer existingTaskId, Integer newTaskId) {
        return taskService.addDependency(existingTaskId, newTaskId);

    }

    private TaskDTO convertTaskToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueBy());
        return taskDTO;
    }
}
