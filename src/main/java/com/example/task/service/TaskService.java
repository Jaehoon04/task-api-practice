package com.example.task.service;


import com.example.task.domain.Task;
import com.example.task.dto.TaskCreateRequest;
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

    public Task createTask(String title, String description){
        Task task = new Task(title,description);
        return taskRepository.save(task);
    }

    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Task getTask(Long id){
        return taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("Task를 찾을 수 없습니다."));


    }

    public Task updateTask(Long id, String title, String description){
        Task task = taskRepository.findById(id).orElseThrow();
        task.update(title,description);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

}
