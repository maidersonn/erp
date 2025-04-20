package com.maider.erp.data.repository;

import com.maider.erp.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByDni(String dni);
}
