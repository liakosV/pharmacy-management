package com.project.pharmacy_project.rest;

import com.project.pharmacy_project.dto.IngredientInsertDto;
import com.project.pharmacy_project.dto.IngredientReadOnlyDto;
import com.project.pharmacy_project.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<IngredientReadOnlyDto> saveIngredient(@RequestBody IngredientInsertDto insertDto) {
        IngredientReadOnlyDto ingredientReadOnlyDto = ingredientService.saveIngredient(insertDto);

        return new ResponseEntity<>(ingredientReadOnlyDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void removeIngredient(@PathVariable Long id) {
        ingredientService.removeIngredient(id);
    }

    @GetMapping
    public ResponseEntity<List<IngredientReadOnlyDto>> getAllIngredients() {
        List<IngredientReadOnlyDto> ingredientReadOnlyDtos = ingredientService.getAllIngredients();

        return new ResponseEntity<>(ingredientReadOnlyDtos, HttpStatus.OK);
    }
}
