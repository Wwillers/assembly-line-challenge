package com.assemblyline.assemblyLine.controller;

import com.assemblyline.assemblyLine.entity.AssemblyLineTask;
import com.assemblyline.assemblyLine.service.AssemblyLineSchedule;
import com.assemblyline.assemblyLine.service.TranslateTaskFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Log4j2
@Controller
public class UploadController {

    @Autowired
    private TranslateTaskFile translateTaskFile;
    @Autowired
    private AssemblyLineSchedule assemblyLineSchedule;

    private final String UPLOAD_DIR = "./src/main/resources/uploads/";

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            List<AssemblyLineTask> translatedList = translateTaskFile.fromAssemblyLineTaskFile(path.toString());
            assemblyLineSchedule.processScheduleInfo(translatedList);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        attributes.addFlashAttribute("message", "See the results in your IDE");

        return "redirect:/";
    }
}
