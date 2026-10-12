package com.example.task.controller;

import com.example.task.domain.TaskStatus;
import com.example.task.dto.TaskResponse;
import com.example.task.exception.TaskNotFoundException;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void getTask_성공() throws Exception {
        //given
        TaskResponse response = new TaskResponse(1L,"Java 공부","Controller 테스트", TaskStatus.TODO);

        when(taskService.getTask(1L)).thenReturn(response);

        //when/then
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Java 공부"))
                .andExpect(jsonPath("$.description").value("Controller 테스트"))
                .andExpect(jsonPath("$.status").value("TODO"));



    }

    @Test
    void createTask()throws Exception{
        //given
        TaskResponse response = new TaskResponse(1L,"Spring 공부","Controller POST 테스트", TaskStatus.TODO);

        when(taskService.createTask("Spring 공부","Controller POST 테스트")).thenReturn(response);

        //when & then
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
               {
                    "title": "Spring 공부",
                    "description": "Controller POST 테스트"  
               }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Spring 공부"))
                .andExpect(jsonPath("$.description").value("Controller POST 테스트"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    void createTask_실패_제목_비어있음()throws Exception{
        //when & then
        mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "title":"",
                        "description":"Validation 테스트" 
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("제목은 비어있을 수 없습니다."));

    }

    @Test
    void createTask_실패_Task가_존재하지않음() throws Exception {
        // given
        when(taskService.getTask(999L))
                .thenThrow(new TaskNotFoundException("Task를 찾을 수가 없습니다."));


        // when & then
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Task를 찾을 수가 없습니다."));
    }

    @Test
    void deleteTask_성공() throws Exception{

        //when & then
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(1L);

    }



    @Test
    void updateTask_성공() throws Exception{
        TaskResponse response = new TaskResponse(1L,"수정된 제목","수정된 내용",TaskStatus.TODO);

        when(taskService.updateTask(1L,"수정된 제목","수정된 내용")).thenReturn(response);

        //when & then

        mockMvc.perform(put("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "title": "수정된 제목",
                        "description": "수정된 내용" 
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("수정된 제목"))
                .andExpect(jsonPath("$.description").value("수정된 내용"))
                .andExpect(jsonPath("$.status").value("TODO"));

    }

}
