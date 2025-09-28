package com.project.pharmacy_project.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInsertDto {

    @NotEmpty(message = "The name cannot be empty.")
    private String name;
}
