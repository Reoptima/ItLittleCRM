package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Comments;
import com.example.itlittlecrm.models.Subsystem;
import com.example.itlittlecrm.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentsRepository extends CrudRepository<Comments, Long> {

}