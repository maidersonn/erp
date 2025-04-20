package com.maider.erp.data;

import com.maider.erp.data.repository.JpaEmployeeProjectRepository;
import com.maider.erp.domain.entities.EmployeeProject;
import com.maider.erp.domain.repositories.EmployeeProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeProjectRepositoryImpl implements EmployeeProjectRepository {
    @Autowired
    private JpaEmployeeProjectRepository jpaRepo;

    @Override
    public EmployeeProject save(EmployeeProject employeeProject) {
        return jpaRepo.save(employeeProject);
    }

    @Override
    public Optional<EmployeeProject> findByEmployeeIdAndProjectId(Long employeeId, Long projectId) {
        return jpaRepo.findByEmployeeIdAndProjectId(employeeId, projectId);
    }
}
