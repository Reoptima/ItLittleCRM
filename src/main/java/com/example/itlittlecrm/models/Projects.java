package com.example.itlittlecrm.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Projects {

    @Id
    @GeneratedValue
    private Long id;

    private String projectName;

    @OneToMany(mappedBy = "projects", cascade = CascadeType.ALL)
    private List<Team> teams;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Subsystem> subsystems = new ArrayList<>();


    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set <Reports> reports;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Subsystem> getSubsystems() {
        return subsystems;
    }

    public void setSubsystems(List<Subsystem> subsystems) {
        this.subsystems = subsystems;
    }

    public Set<Reports> getReports() {
        return reports;
    }

    public void setReports(Set<Reports> reports) {
        this.reports = reports;
    }
}


