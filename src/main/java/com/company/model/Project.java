package com.company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Project {
    private String name;
    private List<Task> taskList;

    public Project(String name) {
        this.name = name;
        this.taskList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task getTaskByName(String name){
        return taskList.stream()
                .filter(task -> task.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
