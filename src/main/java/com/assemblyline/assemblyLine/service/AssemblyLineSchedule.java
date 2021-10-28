package com.assemblyline.assemblyLine.service;

import com.assemblyline.assemblyLine.entity.AssemblyLineTask;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AssemblyLineSchedule {

    private static int MORNING_TIME_MINUTES = 180;
    private static int AFTERNOON_TIME_MINUTES = 240;

    public void processScheduleInfo(List<AssemblyLineTask> taskList) {

        taskList.sort(Comparator.comparing(AssemblyLineTask::getTaskTime).reversed());

        AtomicInteger n = new AtomicInteger(1);
        System.out.println("Linha de montagem " + n);
        AtomicLong plusDuration = new AtomicLong(540);

        taskList.stream().forEach(task -> {
            Duration duration = Duration.ofMinutes(plusDuration.get());
            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();
            if (duration.toMinutes() == 540) {
                System.out.println("09:00 " + task.getTaskName() + " " + task.getTaskTime());
                plusDuration.getAndAdd(task.getTaskTime());
                return;
            }

            if (duration.toMinutes() == 720) {
                System.out.println("12:00 Almoço");
                plusDuration.getAndAdd(60);
                return;
            }

            if (duration.toMinutes() >= 960 && duration.toMinutes() < 1020) {
                System.out.println(String.format("%d:%02d %s %d", hours, minutes, task.getTaskName(), task.getTaskTime()));
                duration = duration.plusMinutes(task.getTaskTime());
                hours = duration.toHours();
                minutes = duration.minusHours(hours).toMinutes();
                System.out.println(String.format("%d:%02d Ginástica laboral", hours, minutes));
                plusDuration.set(540);
                n.getAndIncrement();
                System.out.println("Linha de montagem " + n);
                return;
            }

            System.out.println(String.format("%d:%02d %s %d", hours, minutes, task.getTaskName(), task.getTaskTime()));
            plusDuration.getAndAdd(task.getTaskTime());
        });
    }
}
