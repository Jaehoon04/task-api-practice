package com.example.task.controller;

import com.example.task.domain.Task;
import com.example.task.dto.TaskCreateRequest;
import com.example.task.dto.TaskUpdateRequest;
import com.example.task.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @GetMapping
    public List<Task> GetTask(){
        return taskService.getTasks();
    }

    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody TaskCreateRequest request){
        return taskService.createTask(request.getTitle(),request.getDescription());
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable long id, @RequestBody TaskUpdateRequest request){
        return taskService.updateTask(id, request.getTitle(), request.getDescription());
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
    }

}
