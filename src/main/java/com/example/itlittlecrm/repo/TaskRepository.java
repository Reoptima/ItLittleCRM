package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Subsystem;
import com.example.itlittlecrm.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByTaskNameContains(String taskName);

    Task findByTaskName(String taskName);

    List<Task> findBySubsystemContains(Long subsystemId);
}
