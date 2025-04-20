package com.maider.erp.domain.repositories;

import com.maider.erp.domain.entities.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    boolean existsByDni(String dni);
}
