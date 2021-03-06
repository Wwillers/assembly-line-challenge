package com.assemblyline.assemblyLine.service;

import com.assemblyline.assemblyLine.entity.AssemblyLineTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class AssemblyLineSchedule {

    private static final int MORNING_TIME_MINUTES = 180;
    private static final int AFTERNOON_TIME_MINUTES = 240;
    private static final int MORNING_START_WORK_TIME = 540;
    private static final int AFTERNOON_START_WORK_TIME = 780;

    @Autowired
    private RecursiveSchedule recursiveSchedule;

    public void processScheduleInfo(List<AssemblyLineTask> taskList) {
        int totalScheduleTime = taskList.stream().mapToInt(AssemblyLineTask::getTaskTime).sum();

        List<Integer> scheduleTasksTime = taskList.stream().map(AssemblyLineTask::getTaskTime).collect(Collectors.toList());

        AtomicInteger assemblyNumber = new AtomicInteger(1);
        while (totalScheduleTime != 0) {
            System.out.println(String.format("Linha de montagem %d:", assemblyNumber.getAcquire()));
            List<AssemblyLineTask> tasks = recursiveProcessSchedule(taskList, scheduleTasksTime, MORNING_TIME_MINUTES, true);
            int remainingTime = tasks.stream().mapToInt(AssemblyLineTask::getTaskTime).sum();
            assemblyNumber.getAndIncrement();
            if (remainingTime == 0) {
                break;
            }
            totalScheduleTime -= remainingTime;
        }
    }

    private List<AssemblyLineTask> recursiveProcessSchedule(List<AssemblyLineTask> assemblyLineTasks, List<Integer> scheduleTasksTime, int time, boolean firstSchedule) {
        List<Integer> morningSchedule = recursiveSchedule.findSchedulePossibilities(scheduleTasksTime, time);
        if (firstSchedule) {
            Duration duration = Duration.ofMinutes(MORNING_START_WORK_TIME);
            for (Integer i : morningSchedule) {
                AssemblyLineTask task = assemblyLineTasks.stream().filter(element -> i.equals(element.getTaskTime())).findFirst().orElse(null);
                if (task != null) {
                    assemblyLineTasks.remove(task);
                }
                scheduleTasksTime.remove(scheduleTasksTime.stream().filter(schedule -> schedule.equals(task.getTaskTime())).findFirst().orElse(null));
                outputMorningSchedule(task, duration);
                duration = duration.plusMinutes(task.getTaskTime());
            }
            System.out.println("12:00 Almo??o");
            return recursiveProcessSchedule(assemblyLineTasks, scheduleTasksTime, AFTERNOON_TIME_MINUTES, false);
        }

        Duration duration = Duration.ofMinutes(AFTERNOON_START_WORK_TIME);
        for (Integer i : morningSchedule) {
            AssemblyLineTask task = assemblyLineTasks.stream().filter(element -> i.equals(element.getTaskTime())).findFirst().orElse(null);
            if (task != null) {
                assemblyLineTasks.remove(task);
                scheduleTasksTime.remove(scheduleTasksTime.stream().filter(schedule -> schedule.equals(task.getTaskTime())).findFirst().orElse(null));
                outputAfternoonSchedule(task, duration);
                duration = duration.plusMinutes(task.getTaskTime());
            }
        }
        System.out.println(String.format("%d:%02d Gin??stica laboral", duration.toHours(), duration.minusHours(duration.toHours()).toMinutes()));
        return assemblyLineTasks;
    }

    private void outputMorningSchedule(AssemblyLineTask lineTask, Duration duration) {
        if (duration.toMinutes() == MORNING_START_WORK_TIME) {
            System.out.println("09:00 " + lineTask.getTaskName() + " " + lineTask.getTaskTime());
            return;
        }
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        System.out.println(String.format("%d:%02d %s %d min", hours, minutes, lineTask.getTaskName(), lineTask.getTaskTime()));
    }

    private void outputAfternoonSchedule(AssemblyLineTask lineTask, Duration duration) {
        if (duration.toMinutes() == AFTERNOON_START_WORK_TIME) {
            System.out.println("13:00 " + lineTask.getTaskName() + " " + lineTask.getTaskTime());
            return;
        }
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        System.out.println(String.format("%d:%02d %s %d min", hours, minutes, lineTask.getTaskName(), lineTask.getTaskTime()));
    }
}
