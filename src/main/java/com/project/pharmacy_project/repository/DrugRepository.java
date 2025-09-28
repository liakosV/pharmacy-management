package com.project.pharmacy_project.repository;

import com.project.pharmacy_project.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Long> {
    Optional<Drug> findByName(String name);
}
