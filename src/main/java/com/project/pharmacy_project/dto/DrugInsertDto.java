package com.project.pharmacy_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugInsertDto {

    @NotEmpty(message = "The name must not be empty.")
    private String name;

    @NotNull(message = "The price must not be null.")
    private BigDecimal price;

    @NotNull(message = "The quantity must not be null.")
    @Min(value = 0, message = "Quantity cannot be negative.")
    private int quantity;

    @NotNull(message = "The to order must not be null.")
    @Min(value = 0, message = "To order cannot be negative.")
    private int toOrder;

    @NotNull(message = "The over the counter must not be null.")
    private boolean overTheCounter;

    @NotNull(message = "The ingredients must not be null.")
    private Set<Long> ingredientIds;

    @NotNull(message = "The category must not be null.")
    private Long categoryId;
}
