package com.project.pharmacy_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientReadOnlyDto {
    private Long id;
    private String name;
}
