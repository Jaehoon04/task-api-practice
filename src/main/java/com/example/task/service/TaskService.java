package com.example.task.service;


import com.example.task.domain.Task;
import com.example.task.dto.TaskCreateRequest;
import com.example.task.dto.TaskResponse;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.repository.TaskRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(String title, String description){
        Task task = new Task(title,description);
        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    public List<TaskResponse> getTasks(){
        List<Task>tasks = taskRepository.findAll();
        return tasks.stream().map(this::toResponse).toList();
    }

    private TaskResponse toResponse(Task task){
        return new TaskResponse(task.getId(),task.getTitle(), task.getDescription(), task.getStatus());
    }

    public TaskResponse getTask(Long id){
        Task task =  taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("Task를 찾을 수 없습니다."));
        return toResponse(task);
    }

    public TaskResponse updateTask(Long id, String title, String description){
        Task task = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("Task를 찾을 수 없습니다."));
        task.update(title, description);
        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

}
