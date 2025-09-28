package com.project.pharmacy_project.model;

import com.project.pharmacy_project.model.static_data.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;


}
