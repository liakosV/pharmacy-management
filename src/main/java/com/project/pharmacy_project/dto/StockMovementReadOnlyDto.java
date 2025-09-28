package com.project.pharmacy_project.dto;

import com.project.pharmacy_project.model.static_data.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementReadOnlyDto {

    private Long id;
    private Long drugId;
    private String drugName;
    private String categoryName;
    private int quantity;
    private MovementType movementType;
    private LocalDate movementDate;
}
