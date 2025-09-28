package com.project.pharmacy_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDto {
    private String message;
    private int status;
    private LocalDateTime timestamp = LocalDateTime.now();


}
