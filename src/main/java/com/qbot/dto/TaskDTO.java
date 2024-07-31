package com.qbot.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskDTO {

    public static final String DATE_PATTERN = "yyyy-MM-dd:HH:mm:ss";

    private String description;

    private int id;

    public List<Integer> getDependecies() {
        return dependecies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDependecies(List<Integer> dependecies) {
        this.dependecies = dependecies;
    }

    private List<Integer> dependecies = new ArrayList<>();

    public int getId() {
        return id;
    }

    private LocalDateTime dueDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime due) {
        this.dueDate = due;
    }

    public void setDueDate(String due) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        try {
            this.dueDate = LocalDateTime.parse(due, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date-time format: " + e.getMessage());
            this.dueDate = LocalDateTime.parse(LocalDateTime.now()
              .format(formatter));
        }
    }
}
