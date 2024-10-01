package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String name;
    private int durationInDays;

    private List<Task> dependencies;


    public Task(String name, int durationInDays) {
        this.name = name;
        this.durationInDays = durationInDays;
        this.dependencies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }

    public void addDependency (Task task) {
        dependencies.add(task);
    }
}
