package com.sys.sys.specification;

import com.sys.sys.model.Motorcycle;
import com.sys.sys.controllers.MotorcycleController.MotorcycleFilters;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

public class MotorcycleSpecification {

    public static Specification<Motorcycle> withFilters(MotorcycleFilters filters) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();

            if (filters.plate() != null && !filters.plate().isBlank()) {
                predicates.add(
                    cb.like(cb.lower(root.get("plate")), "%" + filters.plate().toLowerCase() + "%")
                );
            }

            if (filters.brand() != null && !filters.brand().isBlank()) {
                predicates.add(
                    cb.like(cb.lower(root.get("brand")), "%" + filters.brand().toLowerCase() + "%")
                );
            }

            if (filters.model() != null && !filters.model().isBlank()) {
                predicates.add(
                    cb.like(cb.lower(root.get("model")), "%" + filters.model().toLowerCase() + "%")
                );
            }

            if (filters.status() != null) {
                predicates.add(cb.equal(root.get("status"), filters.status()));
            }

            if (filters.modelYear() != null) {
                predicates.add(cb.equal(root.get("model_year"), filters.modelYear()));
            }

            if (filters.modelYearStart() != null && filters.modelYearEnd() != null) {
                predicates.add(cb.between(root.get("model_year"), filters.modelYearStart(), filters.modelYearEnd()));
            } else if (filters.modelYearStart() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("model_year"), filters.modelYearStart()));
            } else if (filters.modelYearEnd() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("model_year"), filters.modelYearEnd()));
            }

            if (filters.kmMin() != null && filters.kmMax() != null) {
                predicates.add(cb.between(root.get("km"), filters.kmMin(), filters.kmMax()));
            } else if (filters.kmMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("km"), filters.kmMin()));
            } else if (filters.kmMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("km"), filters.kmMax()));
            }

            if (filters.yardId() != null) {
                predicates.add(cb.equal(root.get("yard").get("id"), filters.yardId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
