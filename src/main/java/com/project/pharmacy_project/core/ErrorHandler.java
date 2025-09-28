package com.project.pharmacy_project.core;

import com.project.pharmacy_project.core.exceptions.AppObjectAlreadyExistException;
import com.project.pharmacy_project.core.exceptions.AppObjectIllegalStateException;
import com.project.pharmacy_project.core.exceptions.AppObjectNotFoundException;
import com.project.pharmacy_project.dto.ResponseMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(AppObjectAlreadyExistException.class)
    public ResponseEntity<ResponseMessageDto> handleAlreadyExist(AppObjectAlreadyExistException e) {
        return customResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AppObjectNotFoundException.class)
    public ResponseEntity<ResponseMessageDto> handleNotFound(AppObjectNotFoundException e) {
        return customResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppObjectIllegalStateException.class)
    public ResponseEntity<ResponseMessageDto> handleIllegalState(AppObjectIllegalStateException e) {
        return customResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ResponseMessageDto> customResponse(String message, HttpStatus status) {
        ResponseMessageDto messageDto = new ResponseMessageDto(message, status.value(), LocalDateTime.now());
        return new ResponseEntity<>(messageDto, status);
    }
}
