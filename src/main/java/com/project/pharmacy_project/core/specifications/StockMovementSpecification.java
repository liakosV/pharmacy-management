package com.project.pharmacy_project.core.specifications;

import com.project.pharmacy_project.model.StockMovement;
import com.project.pharmacy_project.model.static_data.enums.MovementType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class StockMovementSpecification {

    public static Specification<StockMovement> searchByMovementType(MovementType movementType) {
        return ((root, query, criteriaBuilder) -> {
            if (movementType == null) return null;
            return criteriaBuilder.equal(root.get("movementType"), movementType);
        });

    }

    public static Specification<StockMovement> searchByDrugId(Long id) {
        return ((root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("drug").get("id"), id);
        });
    }

    public static Specification<StockMovement> searchByDate(LocalDate start, LocalDate end) {
        return ((root, query, criteriaBuilder) -> {
            if (start == null && end == null) {
                return null;
            } else if (start != null && end != null) {
                return criteriaBuilder.between(root.get("movementDate"), start, end);
            } else if (start != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("movementDate"), start);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("movementDate"), end);
            }
        });
    }
}
