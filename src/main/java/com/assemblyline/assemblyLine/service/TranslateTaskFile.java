package com.assemblyline.assemblyLine.service;

import com.assemblyline.assemblyLine.entity.AssemblyLineTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TranslateTaskFile {

    @Autowired
    private AssemblyLineSchedule assemblyLineSchedule;

    public List<AssemblyLineTask> translateTaskFileToTaskList(String file) {
        try {
            List<String> tasksList = Files.readAllLines(Paths.get(file));
            List<AssemblyLineTask> tasks = tasksList.stream().map(task -> {
                String taskName = task.substring(0, task.lastIndexOf(" ")).replace("-", "");
                String taskTime = task.substring(task.lastIndexOf(" ") + 1).replace("min", "");
                AssemblyLineTask lineTask = new AssemblyLineTask();
                lineTask.setTaskName(taskName);
                lineTask.setTaskTime(taskTime);
                return lineTask;
            }).collect(Collectors.toList());
            assemblyLineSchedule.processScheduleInfo(tasks);
        } catch (Exception e) {
            log.error("Falha ao traduzir arquivo, causa: {}", e.getMessage());
        }
        return Collections.emptyList();
    }
}
