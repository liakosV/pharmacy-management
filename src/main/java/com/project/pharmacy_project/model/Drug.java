package com.project.pharmacy_project.model;

import com.project.pharmacy_project.model.static_data.Category;
import com.project.pharmacy_project.model.static_data.Ingredient;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drugs")
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "The min value of the price is 0.01")
    private BigDecimal price;

    @Column(nullable = false)
    @Min(0)
    private int quantity;

    @Column(name = "to_order", nullable = false)
    @Min(0)
    private int toOrder;

    @Column(name = "over_the_counter", nullable = false)
    private boolean overTheCounter;

    @ManyToMany
    @JoinTable(
            name = "drug_ingredients",
            joinColumns = @JoinColumn(name = "drug_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "drug", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockMovement> stockMovements = new ArrayList<>();

}
