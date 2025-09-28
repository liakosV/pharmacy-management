package com.project.pharmacy_project.service;

import com.project.pharmacy_project.core.exceptions.AppObjectAlreadyExistException;
import com.project.pharmacy_project.core.exceptions.AppObjectIllegalStateException;
import com.project.pharmacy_project.core.exceptions.AppObjectNotFoundException;
import com.project.pharmacy_project.dto.CategoryInsertDto;
import com.project.pharmacy_project.dto.CategoryReadOnlyDto;
import com.project.pharmacy_project.mapper.Mapper;
import com.project.pharmacy_project.model.static_data.Category;
import com.project.pharmacy_project.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for handling Category business logic.
 * Provides operations for creating, removing, and retrieving categories.
 *
 * Responsibilities:
 * - Prevent duplicate categories by name.
 * - Disallow removal of categories that are in use by Drugs.
 * - Transform entities to DTOs using {@link Mapper}.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    /**
     * Saves a new category if no existing category with the same name exists.
     *
     * @param insertDto The category data to be inserted.
     * @return The saved category as a read-only DTO.
     * @throws AppObjectAlreadyExistException if a category with the same name already exists.
     */
    @Transactional
    public CategoryReadOnlyDto saveCategory(CategoryInsertDto insertDto) {

        if (categoryRepository.findByName(insertDto.getName()).isPresent()) {
            throw new AppObjectAlreadyExistException("The category with name: " + insertDto.getName() + " already exist");
        }

        Category category = categoryRepository.save(mapper.mapToCategoryEntity(insertDto));

        return mapper.mapToCategoryReadOnlyDto(category);
    }

    /**
     * Removes a category by its ID.
     * A category cannot be removed if it is currently referenced by any drugs.
     *
     * @param id The ID of the category to remove.
     * @throws AppObjectNotFoundException if no category is found with the given ID.
     * @throws AppObjectIllegalStateException if the category is used by drugs.
     */
    @Transactional
    public void removeCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppObjectNotFoundException("The category with id: " + id + " was not found."));

        if (!category.getAllDrugs().isEmpty()) throw new AppObjectIllegalStateException("You cannot remove a category that is used by other drugs.");

        categoryRepository.delete(category);
    }

    /**
     * Retrieves all categories as read-only DTOs.
     *
     * @return A list of all categories.
     */
    public List<CategoryReadOnlyDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::mapToCategoryReadOnlyDto)
                .toList();
    }
}
