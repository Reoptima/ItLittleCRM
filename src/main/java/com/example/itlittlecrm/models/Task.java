package com.example.itlittlecrm.models;

import com.example.itlittlecrm.interfaces.IExelExport;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
public class Task implements IExelExport {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sybsystem_id")
    private Subsystem subsystem;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Comments> comments;

    private String taskName, taskText, taskStatus, taskDeadline;

    public String getStatusName() {
        switch (taskStatus) {
            case "lookAt":
                return "На рассмотрении";
            case "inWork":
                return "В работе";
            case "done":
                return "Выполнено";
            case "reWork":
                return "Отправлено на пересмотр";
            default:
                return taskStatus;
        }
    }

    public Subsystem getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(Subsystem subsystem) {
        this.subsystem = subsystem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    @Override
    public List<Object> getHeaders() {
        return Arrays.asList("Название задачи", "Текст задачи", "Статус задачи", "Срок выполнения");
    }

    @Override
    public List<Object> getData() {
        return Arrays.asList(taskName, taskText, getStatusName(), taskDeadline);
    }
}