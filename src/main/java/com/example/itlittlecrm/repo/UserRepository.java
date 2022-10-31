package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByUsernameContains(String username);
    User findByUsername(String username);
}
