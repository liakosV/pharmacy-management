package com.project.pharmacy_project.model.static_data;

import com.project.pharmacy_project.model.Drug;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Drug> drugs = new HashSet<>();

    public Set<Drug> getAllDrugs() {
        if (drugs == null) drugs = new HashSet<>();
        return Collections.unmodifiableSet(drugs);
    }

}
