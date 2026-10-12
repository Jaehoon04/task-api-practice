package com.example.task.service;


import com.example.task.domain.Task;
import com.example.task.domain.TaskStatus;
import com.example.task.dto.TaskResponse;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getTask_성공() {
        // given
        Task task = new Task("Java 공부","자바 공부하기");
        ReflectionTestUtils.setField(task,"id",1L);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        //when
        TaskResponse response = taskService.getTask(1L);

        //then
        assertThat(response.getTitle()).isEqualTo("Java 공부");
        assertThat(response.getDescription()).isEqualTo("자바 공부하기");
        assertThat(response.getStatus()).isEqualTo(TaskStatus.TODO);

    }
    @Test
    void getTask_실패_Task가_존재하지않음(){
        //given
        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());

        //when&then
        assertThatThrownBy(()->taskService.getTask(999L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task를 찾을 수 없습니다.");
    }

    @Test
    void createTask_성공(){
        //given
        Task savedTask = new Task("Java 공부","테스트 코드 공부");
        ReflectionTestUtils.setField(savedTask,"id",1L);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        //when
        TaskResponse response = taskService.createTask("Java 공부","테스트 코드 공부");

        //then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Java 공부");
        assertThat(response.getDescription()).isEqualTo("테스트 코드 공부");
        assertThat(response.getStatus()).isEqualTo(TaskStatus.TODO);
    }

    @Test
    void updateTask_성공(){
        //given
        Task task = new Task("기존 제목","기존 내용");
        ReflectionTestUtils.setField(task,"id",1L);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        //when
        TaskResponse response = taskService.updateTask(1L,"수정 제목","수정 내용");

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("수정 제목");
        assertThat(response.getDescription()).isEqualTo("수정 내용");

    }

    @Test
    void deleteTask_성공(){
        //when
        taskService.deleteTask(1L);

        //then
        verify(taskRepository).deleteById(1L);
    }
}
