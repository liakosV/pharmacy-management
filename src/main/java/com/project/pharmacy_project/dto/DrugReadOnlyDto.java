package com.project.pharmacy_project.dto;

import com.project.pharmacy_project.model.static_data.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugReadOnlyDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private int toOrder;
    private boolean overTheCounter;
    private Set<IngredientReadOnlyDto> ingredients;
    private CategoryReadOnlyDto category;
}
