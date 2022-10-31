package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {
    List<Team> findByTeamNameContains(String teamName);
    Team findByTeamName(String teamName);
}
