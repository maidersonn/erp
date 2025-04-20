package com.maider.erp.domain.repositories;

import com.maider.erp.domain.entities.Project;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Optional<Project> findById(Long id);
    Project save(Project project);

    List<Project> findAvailableProjectsForEmployee(Long employeeId);
}
