package com.maider.erp.domain.services;

import com.maider.erp.domain.entities.Client;
import com.maider.erp.domain.entities.Project;
import com.maider.erp.domain.repositories.ClientRepository;
import com.maider.erp.domain.result.Failure;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import com.maider.erp.domain.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    ClientRepository clientRepository;

    public Result<List<Project>, String> getAll() {
        try {
            List<Project> projects = projectRepository.findAll();
            return new Success<>(projects);
        } catch (Exception e) {
            return new Failure<>("Failed to fetch projects: " + e.getMessage());
        }
    }

    public Result<Project, String> getById(Long id) {
        return projectRepository.findById(id)
                .<Result<Project, String>>map(Success::new)
                .orElseGet(() -> new Failure<>("Project with ID " + id + " not found."));
    }

    public Result<Project, String> createProject(Long clientId, String projectName) {
        return clientRepository.findById(clientId).<Result<Project, String>>map(client -> {
                if (projectName == null || projectName.isEmpty()) {
                    return new Failure<>("Project name can not be empty");
                }
                Project project = new Project();
                project.setName(projectName);
                project.setStartDate(LocalDate.now());
                project.setClient(client);
                try {
                    return new Success<>(projectRepository.save(project));
                } catch (Exception e) {
                    return new Failure<>("An error occured when creating a project" + e.getMessage());
                }
            })
            .orElseGet(() -> new Failure<>("Client with ID " + clientId + " not found."));
    }

    public Result<Project, String> closeProject(Long projectId) {
        return projectRepository.findById(projectId).<Result<Project, String>>map(project -> {
            try {
                project.setEndDate(LocalDate.now());
                project.getEmployeeProjects().forEach(employeeProject -> {
                    employeeProject.setEndDate(LocalDate.now());
                });
                return new Success<>(projectRepository.save(project));
            } catch (Exception e) {
                return new Failure<>("An error occured when closing the project" + e.getMessage());
            }
        }).orElseGet(() -> new Failure<>("Project with ID " + projectId + " not found."));
    }
}
