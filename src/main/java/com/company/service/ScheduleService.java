package com.company.service;

import com.company.model.Project;
import com.company.model.Task;

import java.time.LocalDate;
import java.util.*;

//***

public class ScheduleService {

    public static void scheduleProjects(List<Project> projects) {
        for (Project project : projects) {
            System.out.println("Scheduling project: " + project.getName());

            Map<Task, LocalDate> startDateSchedule = new HashMap<>();
            Map<Task, LocalDate> endDateSchedule = new HashMap<>();

            // track the last end date
            LocalDate lastEndDate = LocalDate.now();

            // get all tasks from the project
            List<Task> tasks = new ArrayList<>(project.getTaskList());

            // schedule task
            while (!tasks.isEmpty()) {
                // filter tasks that can be scheduled (dependencies are done)
                List<Task> schedulableTasks = new ArrayList<>();

                for (Task task : tasks) {
                    if (canScheduleTask(task, startDateSchedule)) {
                        schedulableTasks.add(task);
                    }
                }

                // sort the schedulable tasks by duration
                if (!schedulableTasks.isEmpty()) {
                    schedulableTasks.sort(Comparator.comparingInt(Task::getDurationInDays));

                    // schedule the task with the shortest duration
                    Task taskToSchedule = schedulableTasks.get(0);
                    LocalDate startDate = findEarliestStartDate(taskToSchedule, startDateSchedule, endDateSchedule, lastEndDate);
                    LocalDate endDate = startDate.plusDays(taskToSchedule.getDurationInDays() - 1);
                    startDateSchedule.put(taskToSchedule, startDate);
                    endDateSchedule.put(taskToSchedule, endDate);
                    lastEndDate = endDate.plusDays(1);

                    // remove the scheduled task from the tasks list
                    tasks.remove(taskToSchedule);
                } else {
                    // break to prevent infinite loop (when done)
                    break;
                }
            }

            // print final schedule
            printSchedule(startDateSchedule, endDateSchedule);
        }
    }

    // chk if task can be scheduled (all dependencies should be scheduled)
    private static boolean canScheduleTask(Task task, Map<Task, LocalDate> startDateSchedule) {
        return task.getDependencies().stream()
                .allMatch(startDateSchedule::containsKey);
    }

    // calculate earliest start date for the task based on its dependencies
    private static LocalDate findEarliestStartDate(Task task, Map<Task, LocalDate> startDateSchedule, Map<Task, LocalDate> endDateSchedule, LocalDate lastEndDate) {
        // get end date of all dependencies
        LocalDate dependencyEndDate = task.getDependencies()
                .stream()
                .map(endDateSchedule::get)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(LocalDate.now()); // If no dependencies, start today

        // determine earliest start date
        return dependencyEndDate.isAfter(lastEndDate) ? dependencyEndDate : lastEndDate;
    }

    private static void printSchedule(Map<Task, LocalDate> startDateSchedule, Map<Task, LocalDate> endDateSchedule) {
        startDateSchedule.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // Sort by startDate
                .forEach(entry -> {
                    Task task = entry.getKey();
                    LocalDate startDate = entry.getValue();
                    LocalDate endDate = endDateSchedule.get(task);
                    System.out.printf("Task: %s, Start Date: %s, End Date: %s%n", task.getName(), startDate, endDate);
                });
    }
}