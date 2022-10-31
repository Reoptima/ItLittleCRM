package com.example.itlittlecrm.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team {

    @ManyToMany
    @JoinTable(name = "team_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    public Set<Team> teams;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projects project;

    @Id
    @GeneratedValue
    private Long id;

    private String teamName;

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project1) {
        this.project = project1;
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
