package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Projects;
import com.example.itlittlecrm.models.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Projects, Long> {
    List<Projects> findByProjectNameContains(String projectName);

//    Projects findByProjectName(String projectName);
    List<Projects> findByTeamsContains(Team teams);

    List<Projects> findByTeamsIn(List<Team> teams);
}
