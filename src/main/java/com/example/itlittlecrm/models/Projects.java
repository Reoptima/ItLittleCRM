package com.example.itlittlecrm.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Projects {

    @Id
    @GeneratedValue
    private Long id;

    private String projectName;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Team> teams;
}


