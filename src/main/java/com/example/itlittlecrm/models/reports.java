package com.example.itlittlecrm.models;

import javax.persistence.*;

@Entity
public class reports {
    @Id
    @GeneratedValue
    private Long id;

    private String reportName, reportText;


}
