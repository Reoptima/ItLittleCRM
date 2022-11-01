package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Subsystem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SybsystemRepository extends CrudRepository<Subsystem, Long> {
    List<Subsystem> findBySubsystemNameContains(String subsystemName);
    Subsystem findBySubsystemName(String subsystemName);
}
