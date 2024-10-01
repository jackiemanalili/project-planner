package com.company;

import com.company.model.Project;
import com.company.model.Task;
import com.company.service.ScheduleService;
import com.company.util.Parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String filePath = "src/main/resources/input.txt"; // Adjust the path as necessary
        try {

            List<Project> projects = Parser.parseProject(filePath);
            ScheduleService.scheduleProjects(projects);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

    }
}