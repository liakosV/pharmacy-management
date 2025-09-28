package com.project.pharmacy_project.dto;

import com.project.pharmacy_project.model.static_data.enums.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementInsertDto {

    @NotNull(message = "The name must not be null.")
    private Long drugId;

    @NotNull(message = "The quantity must not be null.")
    @Min(value = 1, message = "The quantity must be at least 1.")
    private int quantity;

    @NotNull(message = "The movement type must not be null.")
    private MovementType movementType;
}
