package com.maider.erp.data.repository;

import com.maider.erp.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        SELECT p FROM Project p
        WHERE p.endDate IS NULL
        AND p.id NOT IN (
            SELECT ep.project.id
            FROM EmployeeProject ep
            WHERE ep.employee.id = :employeeId
            AND ep.endDate IS NULL
        )
        """)
    List<Project> findAvailableProjectsForEmployee(@Param("employeeId") Long employeeId);
}
