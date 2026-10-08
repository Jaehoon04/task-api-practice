package com.example.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskUpdateRequest {
    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    private String title;
    private String description;
}
