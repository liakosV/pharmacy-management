package com.project.pharmacy_project.dto;

import com.project.pharmacy_project.model.static_data.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugReadOnlyDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private CategoryReadOnlyDto category;
}
