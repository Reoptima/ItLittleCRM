package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findByNameContains(String name);
    Client findByName(String name);
}
