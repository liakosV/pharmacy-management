package com.project.pharmacy_project.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientInsertDto {

    @NotEmpty(message = "The name must not be empty.")
    private String name;
}
