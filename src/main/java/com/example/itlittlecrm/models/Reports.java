package com.example.itlittlecrm.models;

import javax.persistence.*;

@Entity
public class Reports {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projects projects;

    private String reportName, reportText;

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

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }
}
