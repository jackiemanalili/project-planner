package com.company.util;


import com.company.model.Project;
import com.company.model.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Input data format
 * <p>
 * Project: Project Name
 * TaskName, Duration by number of days, Dependencies separated by comma
 * </p>
 *
 * <p>
 * Example:
 * Project: Project A
 * Task1, 5, Task2, Task3
 * Task2, 2
 * Task3, 3
 * </p>
 */


public class Parser {

    public static List<Project> parseProject (String filePath) {
        List<Project> projects = new ArrayList<>();
        Project currentProject = null;


        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                //check/create new project
                if(line.toUpperCase().startsWith("PROJECT:")) {
                    //check if there are pending dependency mapping


                    if (currentProject != null) {
                        projects.add(currentProject);
                    }

                    String projectName = line.substring("PROJECT:".length()).trim();
                    currentProject = new Project(projectName);
                } else if (!line.isEmpty() && currentProject != null){

                    //check/create new task
                    String[] details = line.split(",");
                    String taskName = details[0].trim();
                    int duration = Integer.parseInt(details[1].trim());
                    Task task = new Task(taskName, duration);

                    // check/add dependencies if any v1
                    for (int i = 2; i<details.length; i++) {
                        String dependencyName = details[i].trim();
                        Task dependencyTask = currentProject.getTaskByName(dependencyName);

                        if (dependencyTask != null) {
                            task.addDependency(dependencyTask);
                        }
                    }

                    currentProject.addTask(task);
                }




            }

            // Add the last project if it exists
            if (currentProject != null) {
                projects.add(currentProject);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return projects;
    }

}
