package com.maider.erp.data;

import com.maider.erp.data.repository.JpaProjectRepository;
import com.maider.erp.domain.entities.Project;
import com.maider.erp.domain.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    @Autowired
    private JpaProjectRepository jpaRepo;

    @Override
    public List<Project> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public Project save(Project project) {
        return jpaRepo.save(project);
    }

    @Override
    public List<Project> findAvailableProjectsForEmployee(Long employeeId) {
        return jpaRepo.findAvailableProjectsForEmployee(employeeId);
    }
}
