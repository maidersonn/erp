package com.maider.erp.data;

import com.maider.erp.data.repository.JpaEmployeeRepository;
import com.maider.erp.domain.entities.Employee;
import com.maider.erp.domain.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Autowired
    private JpaEmployeeRepository jpaRepo;

    @Override
    public List<Employee> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public Employee save(Employee employee) {
        return jpaRepo.save(employee);
    }


    @Override
    public boolean existsByDni(String dni) {
        return jpaRepo.existsByDni(dni);
    }
}
