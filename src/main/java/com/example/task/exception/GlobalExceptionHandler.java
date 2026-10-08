package com.example.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // NOT_FOUND --> http error 404
    public ErrorResponse handleTaskNotFound(TaskNotFoundException e){
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // BAD_REQUEST --> http error 400
    public ErrorResponse handleValidation(MethodArgumentNotValidException e){
        String message = e.getBindingResult().getFieldError().getDefaultMessage();

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),message);
    }
}
