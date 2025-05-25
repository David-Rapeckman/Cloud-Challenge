package com.sys.sys.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sys.sys.model.Yard;

public interface YardRepository extends JpaRepository<Yard, Long> {

    Page<Yard> findByMaxCapacityLessThanEqual(Integer maxCapacity, Pageable pageable);
}