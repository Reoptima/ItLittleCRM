package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Sales;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SalesRepository extends CrudRepository<Sales, Long> {
    List<Sales> findBySaleNameContains(String salesName);

    Sales findBySaleName(String salesName);
}
