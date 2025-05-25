package com.sys.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sys.sys.model.Motorcycle;
import com.sys.sys.model.Yard;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long>, JpaSpecificationExecutor<Motorcycle> {

    long countByYard(Yard yard);
    boolean existsByPlate(String plate);

}
