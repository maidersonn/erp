package com.maider.erp.domain.repositories;

import com.maider.erp.domain.entities.Employee;
import com.maider.erp.domain.entities.EmployeeProject;

import java.util.List;
import java.util.Optional;

public interface EmployeeProjectRepository {
    EmployeeProject save(EmployeeProject employeeProject);

    Optional<EmployeeProject> findByEmployeeIdAndProjectId(Long employeeId, Long projectId);
}
