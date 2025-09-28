package com.project.pharmacy_project.rest;

import com.project.pharmacy_project.dto.CategoryInsertDto;
import com.project.pharmacy_project.dto.CategoryReadOnlyDto;
import com.project.pharmacy_project.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryReadOnlyDto> saveCategory(@RequestBody CategoryInsertDto insertDto) {
        CategoryReadOnlyDto categoryReadOnlyDto = categoryService.saveCategory(insertDto);

        return new ResponseEntity<>(categoryReadOnlyDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
    }

    @GetMapping
    public ResponseEntity<List<CategoryReadOnlyDto>> getAllCategories() {
        List<CategoryReadOnlyDto> categoriesList = categoryService.getAllCategories();
        return new ResponseEntity<>(categoriesList, HttpStatus.OK);
    }
}
