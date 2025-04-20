package com.maider.erp.web.controllers;

import com.maider.erp.domain.entities.Project;
import com.maider.erp.domain.result.Failure;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import com.maider.erp.domain.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public String getAll(Model model) {
        Result<List<Project>, String> result = projectService.getAll();
        if (result instanceof Success<?, ?>) {
            model.addAttribute("projects", result.getValue());
        } else {
            model.addAttribute("error", result.getError());
        }
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String getProjectDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Result<Project, String> result = projectService.getById(id);

        if (result instanceof Success<?, ?>) {
            model.addAttribute("project", result.getValue());
            return "project_detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "An error occurred fetching project " + id);
            return "redirect:/projects";
        }
    }

    @PostMapping("projects/create")
    public String createProjectForClient(@RequestParam Long clientId,
                                         @RequestParam String name,
                                         RedirectAttributes redirectAttributes) {

        Result<Project, String> result = projectService.createProject(clientId, name);

        if (result instanceof Success<Project, String>) {
            redirectAttributes.addFlashAttribute("success", "Project created successfully.");
        } else if (result instanceof Failure<Project, String>) {
            redirectAttributes.addFlashAttribute("error", result.getError());
        }
        return "redirect:/clients/" + clientId;
    }

    @GetMapping("/projects/close/{id}")
    public String closeProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Result<Project, String> result = projectService.closeProject(id);
        if (result instanceof Success<?, ?>) {
            redirectAttributes.addFlashAttribute("success", "Project closed");
        } else {
            redirectAttributes.addFlashAttribute("error", result.getError());
        }
        return "redirect:/projects";
    }
}
