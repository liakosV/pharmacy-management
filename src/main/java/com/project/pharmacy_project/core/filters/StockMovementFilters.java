package com.project.pharmacy_project.core.filters;

import com.project.pharmacy_project.model.static_data.enums.MovementType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockMovementFilters {

    @Nullable
    private MovementType movementType;

    @Nullable
    private Long drugId;

    @Nullable
    private LocalDate startDate;

    @Nullable
    private LocalDate endDate;
}
