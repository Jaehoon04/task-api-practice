package com.example.task.dto;

import com.example.task.domain.Task;
import com.example.task.domain.TaskStatus;
import lombok.Getter;

@Getter
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
}
