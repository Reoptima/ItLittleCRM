package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Projects;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Projects, Long> {
    List<Projects> findByProjectNameContains(String projectName);

    Projects findByProjectName(String projectName);
    @Query(value = "select u from User u where u.id in (select t.users from Team t where t.projects = ?1)", nativeQuery = true)
    List<Projects> findByTeamsUsersUsername(String s);
}

