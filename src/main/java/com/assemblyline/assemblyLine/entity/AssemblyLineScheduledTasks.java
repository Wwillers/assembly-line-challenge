package com.assemblyline.assemblyLine.entity;

import lombok.Data;

@Data
public class AssemblyLineScheduledTasks {

    private int hour;
    private String taskName;
    private String taskTime;
}
