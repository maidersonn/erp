package com.maider.erp.data.repository;

import com.maider.erp.domain.entities.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaEmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
    Optional<EmployeeProject> findByEmployeeIdAndProjectId(Long employeeId, Long projectId);
}
