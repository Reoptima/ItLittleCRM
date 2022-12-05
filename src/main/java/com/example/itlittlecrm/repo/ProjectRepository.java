package com.example.itlittlecrm.repo;

import com.example.itlittlecrm.models.Projects;
import com.example.itlittlecrm.models.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Projects, Long> {
    List<Projects> findByProjectNameContains(String projectName);

    Projects findByProjectName(String projectName);

    //    @Query(value = "select u from user u where u.id in (select t.user_id from team_user t where t.projects = ?1)", nativeQuery = true)
//    List<Projects> findByTeamsUsersUsername(String s);
    List<Projects> findByTeamsContains(Team teams);
//    List<Projects> findByUsers(User user);
}

