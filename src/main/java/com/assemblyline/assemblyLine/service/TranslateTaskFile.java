package com.assemblyline.assemblyLine.service;

import com.assemblyline.assemblyLine.entity.AssemblyLineTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TranslateTaskFile {

    public List<AssemblyLineTask> fromAssemblyLineTaskFile(String file) {
        try {
            List<String> taskList = Files.readAllLines(Paths.get(file));
            return toAssemblyLineTaskList(taskList);
        } catch (Exception e) {
            log.error("Falha ao traduzir arquivo, causa: {}", e.getMessage());
            log.debug(e);
        }
        return Collections.emptyList();
    }

    private List<AssemblyLineTask> toAssemblyLineTaskList(List<String> taskList) {
        return taskList.stream().map(task -> {
            String taskName = task.substring(0, task.lastIndexOf(" ")).replace("-", "");
            String replacedMaintenance = task.replace("maintenance", "5");
            String taskTime = replacedMaintenance.substring(task.lastIndexOf(" ") + 1).replace("min", "");
            AssemblyLineTask lineTask = new AssemblyLineTask();
            lineTask.setTaskName(taskName);
            lineTask.setTaskTime(Integer.parseInt(taskTime));
            return lineTask;
        }).collect(Collectors.toList());
    }
}
