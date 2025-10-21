package com.project.pharmacy_project.service;

import com.project.pharmacy_project.core.exceptions.AppObjectAlreadyExistException;
import com.project.pharmacy_project.core.exceptions.AppObjectIllegalStateException;
import com.project.pharmacy_project.core.exceptions.AppObjectNotFoundException;
import com.project.pharmacy_project.dto.IngredientInsertDto;
import com.project.pharmacy_project.dto.IngredientReadOnlyDto;
import com.project.pharmacy_project.mapper.Mapper;
import com.project.pharmacy_project.model.static_data.Ingredient;
import com.project.pharmacy_project.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IngredientService {

    private final Mapper mapper;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public IngredientReadOnlyDto saveIngredient(IngredientInsertDto insertDto) {
        if (ingredientRepository.findByName(insertDto.getName()).isPresent()) {
            throw new AppObjectAlreadyExistException("Ingredient with name: " + insertDto.getName() + " already exists.");
        }

        Ingredient ingredient = ingredientRepository.save(mapper.mapToIngredientEntity(insertDto));

        return mapper.mapToIngredientReadOnlyDto(ingredient);
    }

    @Transactional
    public void removeIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                        .orElseThrow(() -> new AppObjectNotFoundException("The ingredient was not found."));

        if (!ingredient.getDrugs().isEmpty()) throw new AppObjectIllegalStateException("You cannot remove ingredient that is used by other drugs");

        ingredientRepository.delete(ingredient);
    }

    public List<IngredientReadOnlyDto> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(mapper::mapToIngredientReadOnlyDto)
                .toList();
    }
}
