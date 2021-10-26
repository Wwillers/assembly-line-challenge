package com.assemblyline.assemblyLine.service;

import com.assemblyline.assemblyLine.entity.AssemblyLineTask;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssemblyLineSchedule {

    private static int MORNING_TIME_MINUTES = 180;
    private static int AFTERNOON_TIME_MINUTES = 240;

    public void processScheduleInfo(List<AssemblyLineTask> tasksList) {
        List<String> tasksTime = tasksList.stream().map(AssemblyLineTask::getTaskTime).collect(Collectors.toList());
        int totalScheduleTime = sumTotalScheduleTime(tasksTime);


    }

    private int sumTotalScheduleTime(List<String> tasksTime) {
        List<Integer> times = tasksTime.stream().map(this::subsMaintenanceTime).collect(Collectors.toList());
        return times.stream().reduce(0, Integer::sum);
    }

    private int subsMaintenanceTime(String taskTime) {
        String parseMaintenanceTime = taskTime.replace("maintenance", "5");
        return Integer.parseInt(parseMaintenanceTime);
    }
}
