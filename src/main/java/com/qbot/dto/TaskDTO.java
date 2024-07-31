package com.qbot.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.hibernate.validator.constraints.NotBlank;

public class TaskDTO {

    public static final String DATE_PATTERN = "yyyy-MM-dd:HH:mm:ss";

    @NotBlank(message = "Description is mandatory")
    private String description;

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
