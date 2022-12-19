package com.example.itlittlecrm.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Subsystem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projects projects;

    @OneToMany(mappedBy = "subsystem", cascade = CascadeType.ALL)
    private Set<Task> tasks;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    private String subsystemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Projects getProjects() {
        return projects;
    }

    public void setProjects(Projects project) {
        this.projects = project;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }
}
