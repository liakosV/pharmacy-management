package com.project.pharmacy_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReadOnlyDto {

    private Long id;
    private String name;
}
