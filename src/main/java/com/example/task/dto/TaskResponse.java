package com.example.task.dto;

import com.example.task.domain.Task;
import com.example.task.domain.TaskStatus;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;

    public TaskResponse(long id, String title, String description, TaskStatus staus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = staus;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }




}
