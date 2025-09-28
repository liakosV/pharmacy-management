package com.project.pharmacy_project.service;

import com.project.pharmacy_project.core.exceptions.AppObjectIllegalStateException;
import com.project.pharmacy_project.core.exceptions.AppObjectNotFoundException;
import com.project.pharmacy_project.core.filters.StockMovementFilters;
import com.project.pharmacy_project.core.specifications.StockMovementSpecification;
import com.project.pharmacy_project.dto.StockMovementInsertDto;
import com.project.pharmacy_project.dto.StockMovementReadOnlyDto;
import com.project.pharmacy_project.mapper.Mapper;
import com.project.pharmacy_project.model.Drug;
import com.project.pharmacy_project.model.StockMovement;
import com.project.pharmacy_project.model.static_data.enums.MovementType;
import com.project.pharmacy_project.repository.DrugRepository;
import com.project.pharmacy_project.repository.StockMovementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing Stock Movements of drugs.
 * Handles saving movements and querying with optional filters.
 * Ensures business rules like stock availability for outbound movements.
 */
@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final DrugRepository drugRepository;
    private final Mapper mapper;

    /**
     * Saves a stock movement and updates the associated drug's quantity.
     * - INBOUND movement increases the drug quantity.
     * - OUTBOUND movement decreases the drug quantity.
     * Throws an exception if an outbound movement would result in negative stock.
     *
     * @param insertDto DTO containing movement data.
     * @return DTO representing the saved stock movement.
     * @throws AppObjectNotFoundException if the drug is not found.
     * @throws AppObjectIllegalStateException if outbound movement exceeds stock.
     */
    @Transactional
    public StockMovementReadOnlyDto saveMovement(StockMovementInsertDto insertDto) {

        Drug drug = drugRepository.findById(insertDto.getDrugId())
                .orElseThrow(() -> new AppObjectNotFoundException("The drug with id: " + insertDto.getDrugId() + " was not found"));

        // Map DTO -> entity (but not save yet)
        StockMovement stockMovement = mapper.mapToStockMovementEntity(insertDto, drug);

        // Update drug quantity based on movement type
        if (stockMovement.getMovementType() == MovementType.INBOUND) {
            drug.setQuantity(drug.getQuantity() + stockMovement.getQuantity());
        } else {
            int newQuantity = drug.getQuantity() - stockMovement.getQuantity();
            if (newQuantity < 0) {
                throw new AppObjectIllegalStateException("You cannot perform outbound movement. Drug " + drug.getName() + " has insufficient stock.");
            }
            drug.setQuantity(newQuantity);
        }

        // Save movement (drug will be updated automatically because of transactional context)
        stockMovementRepository.save(stockMovement);

        return mapper.mapToStockMovementReadOnyDto(stockMovement);
    }

    /**
     * Retrieves a list of stock movements based on optional filters.
     * Filters include movement type, drug name, and date range.
     *
     * @param filters Object containing optional filter parameters.
     * @return List of read-only DTOs matching the filters.
     */
    @Transactional
    public List<StockMovementReadOnlyDto> getFilteredMovements(StockMovementFilters filters) {
        return stockMovementRepository.findAll(getSpecsFromFilter(filters)).stream()
                .map(mapper::mapToStockMovementReadOnyDto)
                .toList();
    }

    /**
     * This method have been commented out because from the SpringBoot version 3.5
     * the where() method have been marked as deprecated
     */
//    private Specification<StockMovement> getSpecsFromFilter(StockMovementFilters filters) {
//        return Specification
//                .where(StockMovementSpecification.searchByMovementType(filters.getMovementType()))
//                .and(StockMovementSpecification.searchByDrugName(filters.getDrugName()))
//                .and(StockMovementSpecification.searchByDate(filters.getStartDate(), filters.getEndDate()));
//    }

    /**
     * Builds a dynamic Specification for filtering stock movements.
     * Replaces the deprecated Specification.where() method.
     *
     * @param filters The filters containing movementType, drugName, startDate, endDate.
     * @return Specification to be used in repository query.
     */
    private Specification <StockMovement> getSpecsFromFilter(StockMovementFilters filters) {
        Specification<StockMovement> spec = StockMovementSpecification.searchByMovementType(filters.getMovementType());

        spec = spec.and(StockMovementSpecification.searchByDrugId(filters.getDrugId()));
        spec = spec.and(StockMovementSpecification.searchByDate(filters.getStartDate(), filters.getEndDate()));

        return spec;
    }
}
