package com.project.pharmacy_project.repository;

import com.project.pharmacy_project.model.static_data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
