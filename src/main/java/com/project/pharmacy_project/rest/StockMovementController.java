package com.project.pharmacy_project.rest;

import com.project.pharmacy_project.core.filters.StockMovementFilters;
import com.project.pharmacy_project.dto.StockMovementInsertDto;
import com.project.pharmacy_project.dto.StockMovementReadOnlyDto;
import com.project.pharmacy_project.model.static_data.enums.MovementType;
import com.project.pharmacy_project.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping
    public ResponseEntity<StockMovementReadOnlyDto> saveMovement(@RequestBody StockMovementInsertDto insertDto) {
        StockMovementReadOnlyDto stockMovementReadOnlyDto = stockMovementService.saveMovement(insertDto);
        return new ResponseEntity<>(stockMovementReadOnlyDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StockMovementReadOnlyDto>> getFilteredMovements(
            @RequestParam(required = false) MovementType movementType,
            @RequestParam(required = false, name = "Drug Id") Long drugId,
            @RequestParam(required = false, defaultValue = "2025-09-01") LocalDate startDate,
            @RequestParam(required = false, defaultValue = "2025-09-15") LocalDate endDate
    ) {
        StockMovementFilters filters = new StockMovementFilters(movementType, drugId, startDate, endDate);
        List<StockMovementReadOnlyDto> movementList = stockMovementService.getFilteredMovements(filters);
        return ResponseEntity.ok(movementList);
    }
}
