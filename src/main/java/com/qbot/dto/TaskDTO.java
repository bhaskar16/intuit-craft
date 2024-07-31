package com.qbot.dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.NotBlank;

public class TaskDTO {

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

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
