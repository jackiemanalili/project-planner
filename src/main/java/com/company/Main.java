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

           // display input
            for (Project project : projects) {
                System.out.println("project:::: " + project.getName());
                for (Task task : project.getTaskList()) {
                    System.out.print("  task::: " + task.getName() + "---duration::::: " + task.getDurationInDays() + "---dependencies:::: ");
                    for (Task dep : task.getDependencies()) {
                        System.out.print(dep.getName() + " ");
                    }
                    System.out.println();
                }
            }

            System.out.println("==================");
            ScheduleService.scheduleProjects(projects);


        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

    }
}