package com.example.itlittlecrm.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Team {

    @ManyToMany
    @JoinTable(name = "team_user",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    public List<User> users;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projects projects;

    @Id
    @GeneratedValue
    private Long id;

    private String teamName;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
