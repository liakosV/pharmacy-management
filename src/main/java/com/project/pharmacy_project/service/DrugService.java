package com.project.pharmacy_project.service;

import com.project.pharmacy_project.core.exceptions.AppObjectAlreadyExistException;
import com.project.pharmacy_project.core.exceptions.AppObjectIllegalStateException;
import com.project.pharmacy_project.core.exceptions.AppObjectNotFoundException;
import com.project.pharmacy_project.dto.DrugInsertDto;
import com.project.pharmacy_project.dto.DrugReadOnlyDto;
import com.project.pharmacy_project.mapper.Mapper;
import com.project.pharmacy_project.model.Drug;
import com.project.pharmacy_project.model.static_data.Category;
import com.project.pharmacy_project.repository.CategoryRepository;
import com.project.pharmacy_project.repository.DrugRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service layer for handling business logic related to Drugs.
 *
 * Responsibilities:
 *   Ensure drug names are unique.
 *   Enforce that every drug belongs to an existing category.
 *   Provide CRUD-style operations for drugs.
 *   Transform entities into DTOs via the {@link Mapper}.
 */
@Service
@RequiredArgsConstructor
public class DrugService {

    private final DrugRepository drugRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    /**
     * Creates and saves a new drug.
     *
     * @param insertDto the DTO containing the drug's name, price, quantity, and categoryId
     * @return a read-only DTO of the saved drug
     * @throws AppObjectAlreadyExistException if a drug with the same name already exists
     * @throws AppObjectNotFoundException if the specified category does not exist
     */
    @Transactional
    public DrugReadOnlyDto saveDrug(DrugInsertDto insertDto) {

        if (drugRepository.findByName(insertDto.getName()).isPresent()) {
            throw new AppObjectAlreadyExistException("The drug with name: " + insertDto.getName()+ " already exists.");
        }

        Category category = categoryRepository.findById(insertDto.getCategoryId())
                .orElseThrow(() -> new AppObjectNotFoundException("The category with id: " + insertDto.getCategoryId() + " was not found."));

        if (insertDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppObjectIllegalStateException("You cannot insert a new drug with price: " + insertDto.getPrice() + ". (Min value: 0.01)");
        }

        if (insertDto.getQuantity() <= 0) {
            throw new AppObjectIllegalStateException("You cannot insert a new drug with quantity: " + insertDto.getQuantity() + ". (Min value: 1)");
        }

        Drug drug = drugRepository.save(mapper.mapToDrugEntity(insertDto, category));

        return mapper.mapToDrugReadOnlyDto(drug );
    }

    /**
     * Removes a drug by its ID.
     *
     * @param id the ID of the drug to remove
     * @throws AppObjectNotFoundException if no drug is found with the given ID
     */
    @Transactional
    public void removeDrug(Long id) {
        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("The drug with id: " + id + " was not found"));

        drugRepository.delete(drug);
    }


    /**
     * Retrieves all drugs as read-only DTOs.
     *
     * @return a list of all drugs
     */
    public List<DrugReadOnlyDto> getAllDrugs() {

        return drugRepository.findAll().stream()
                .map(mapper::mapToDrugReadOnlyDto)
                .toList();
    }
}
