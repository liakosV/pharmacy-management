package com.project.pharmacy_project.mapper;

import com.project.pharmacy_project.dto.*;
import com.project.pharmacy_project.model.Drug;
import com.project.pharmacy_project.model.StockMovement;
import com.project.pharmacy_project.model.static_data.Category;
import com.project.pharmacy_project.model.static_data.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Utility component responsible for converting between DTOs and entities.
 * Handles mapping logic for categories, drugs and stock movements.
 */
@Component
@RequiredArgsConstructor
public class Mapper {

    /**
     * Converts a {@link Category} entity to {@link CategoryReadOnlyDto}.
     *
     * @param category the category entity.
     * @return the mapped CategoryReadOnlyDto
     */
    public CategoryReadOnlyDto mapToCategoryReadOnlyDto(Category category) {
        if (category == null) return null;

        var dto = new CategoryReadOnlyDto();

        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

    /**
     * Converts a {@link CategoryInsertDto} to a {@link Category} entity.
     *
     * @param insertDto the insert Dto.
     * @return the mapped Category entity.
     */
    public Category mapToCategoryEntity(CategoryInsertDto insertDto) {
        Category category = new Category();

        category.setName(insertDto.getName());

        return category;
    }

    /**
     * Converts a {@link Drug} entity to {@link DrugReadOnlyDto}.
     *
     * @param drug drug entity
     * @return the mapped DrugReadOnlyDto
     */
    public DrugReadOnlyDto mapToDrugReadOnlyDto(Drug drug) {
        if (drug == null) return null;

        var dto = new DrugReadOnlyDto();

        dto.setId(drug.getId());
        dto.setName(drug.getName());
        dto.setPrice(drug.getPrice());
        dto.setQuantity(drug.getQuantity());
        dto.setToOrder(drug.getToOrder());
        dto.setOverTheCounter(drug.isOverTheCounter());
        dto.setIngredients(drug.getIngredients() != null
                ? mapToIngredientReadOnlyDtos(drug.getIngredients())
                : Collections.emptySet());
        dto.setCategory(drug.getCategory() != null
                ? mapToCategoryReadOnlyDto(drug.getCategory())
                : null);


        return dto;
    }

    /**
     * Converts a {@link DrugInsertDto} to a {@link Drug} entity.
     *
     * @param insertDto the DrugInsertDto
     * @param category the Category entity to assign to the Drug
     * @return the mapped Drug entity
     */
    public Drug mapToDrugEntity(DrugInsertDto insertDto, Category category, Set<Ingredient> ingredients) {
        Drug drug = new Drug();

        drug.setName(insertDto.getName());
        drug.setPrice(insertDto.getPrice());
        drug.setQuantity(insertDto.getQuantity());
        drug.setToOrder(insertDto.getToOrder());
        drug.setIngredients(ingredients);
        drug.setOverTheCounter(insertDto.isOverTheCounter());
        drug.setCategory(category);

        return drug;
    }

    /**
     * Converts a {@link StockMovement} entity to {@link StockMovementReadOnlyDto}.
     *
     * @param stockMovement the StockMovement entity
     * @return the mapped StockMovementReadOnlyDto
     */
    public StockMovementReadOnlyDto mapToStockMovementReadOnyDto(StockMovement stockMovement) {
        if (stockMovement == null) return null;

        var dto = new StockMovementReadOnlyDto();

        dto.setId(stockMovement.getId());
        dto.setDrugId(stockMovement.getDrug().getId());
        dto.setDrugName(stockMovement.getDrug().getName());
        dto.setCategoryName(stockMovement.getDrug().getCategory().getName());
        dto.setQuantity(stockMovement.getQuantity());
        dto.setMovementType(stockMovement.getMovementType());
        dto.setMovementDate(stockMovement.getMovementDate());

        return dto;
    }

    /**
     * Converts a {@link StockMovementInsertDto} to a {@link StockMovement} entity.
     *
     * @param insertDto the StockMovementInsertDto
     * @param drug the Drug entity to assign to the StockMovementEntity
     * @return the mapped StockMovement entity
     */
    public  StockMovement mapToStockMovementEntity(StockMovementInsertDto insertDto, Drug drug) {
        StockMovement stockMovement = new StockMovement();

        stockMovement.setDrug(drug);
        stockMovement.setQuantity(insertDto.getQuantity());
        stockMovement.setMovementType(insertDto.getMovementType());
        stockMovement.setMovementDate(LocalDate.now());

        return stockMovement;
    }

    public IngredientReadOnlyDto mapToIngredientReadOnlyDto(Ingredient ingredient) {
        if (ingredient == null) return  null;

        var dto = new IngredientReadOnlyDto();

        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());

        return dto;
    }

    public Set<IngredientReadOnlyDto> mapToIngredientReadOnlyDtos(Set<Ingredient> ingredients) {
        if (ingredients == null) return Collections.emptySet();

        return ingredients.stream()
                .map(this::mapToIngredientReadOnlyDto)
                .collect(Collectors.toSet());

    }

    public Ingredient mapToIngredientEntity(IngredientInsertDto insertDto) {
        Ingredient ingredient = new Ingredient();

        ingredient.setName(insertDto.getName());

        return ingredient;
    }
}
