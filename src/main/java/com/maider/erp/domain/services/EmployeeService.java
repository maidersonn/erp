package com.maider.erp.domain.services;

import com.maider.erp.domain.entities.Employee;
import com.maider.erp.domain.entities.EmployeeProject;
import com.maider.erp.domain.entities.Project;
import com.maider.erp.domain.repositories.EmployeeProjectRepository;
import com.maider.erp.domain.repositories.EmployeeRepository;
import com.maider.erp.domain.repositories.ProjectRepository;
import com.maider.erp.domain.result.Failure;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    public Result<List<Employee>, String> getAll() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            return new Success<>(employees);
        } catch (Exception e) {
            return new Failure<>("Failed to retrieve employees: " + e.getMessage());
        }
    }

    public Result<Employee, String> getById(Long id) {
        return employeeRepository.findById(id)
                .<Result<Employee, String>>map(Success::new)
                .orElseGet(() -> new Failure<>("Employee not found with ID: " + id));
    }

    public Result<Employee, String> createEmployee(String firstName, String lastName, String dni, String companyRole) {
        if (firstName == null || firstName.isEmpty() ||
                lastName == null || lastName.isEmpty() ||
                dni == null || dni.isEmpty() ||
                companyRole == null || companyRole.isEmpty()) {
            return new Failure<>("All fields must be filled out.");
        }

        if (!isValidDni(dni)) {
            return new Failure<>("Provided DNI is not valid");
        }

        if (employeeRepository.existsByDni(dni)) {
            return new Failure<>("An employee with this DNI already exists.");
        }
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDni(dni);
        employee.setCompanyRole(companyRole);
        employee.setStartDate(LocalDate.now());
        try {
            return new Success<>(employeeRepository.save(employee));
        } catch (Exception e) {
            return new Failure<>("Failed to create employee: " + e.getMessage());
        }
    }

    public boolean isValidDni(String dni) {
        if (dni == null || !dni.matches("^\\d{8}[A-HJ-NP-TV-Z]$")) {
            return false;
        }

        String numberPart = dni.substring(0, 8);
        char letter = dni.charAt(8);

        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int number = Integer.parseInt(numberPart);
        char expectedLetter = letters.charAt(number % 23);

        return letter == expectedLetter;
    }

    public Result<Boolean, String> terminate(Long id) {
        return employeeRepository.findById(id)
                .<Result<Boolean, String>>map(employee -> {
                    employee.setEndDate(LocalDate.now());
                    employee.getEmployeeProjects().forEach(employeeProject -> {
                        employeeProject.setEndDate(LocalDate.now());
                    });
                    employeeRepository.save(employee);
                    return new Success<>(true);
                })
                .orElseGet(() -> new Failure<>("Employee not found with ID: " + id));
    }

    public Result<Boolean, String> assignProject(Long employeeId, Long projectId, String role) {
        try {
            return employeeRepository.findById(employeeId).map(employee ->
                    projectRepository.findById(projectId).<Result<Boolean, String>>map(project -> {
                        EmployeeProject assignment = new EmployeeProject();
                        assignment.setEmployee(employee);
                        assignment.setProject(project);
                        assignment.setStartDate(LocalDate.now());
                        assignment.setProjectRole(role);
                        employeeProjectRepository.save(assignment);
                        return new Success<>(true);
                    }).orElseGet(() -> new Failure<>("Project not found with ID: " + projectId))).orElseGet(() -> new Failure<>("Employee not found with ID: " + employeeId));
        } catch (Exception e) {
            return new Failure<>("Failed to assign project: " + e.getMessage());
        }
    }

    public Result<Boolean, String> unassignProject(Long employeeId, Long projectId) {
        try {
            return employeeProjectRepository
                    .findByEmployeeIdAndProjectId(employeeId, projectId)
                    .<Result<Boolean, String>>map(assignment -> {
                        assignment.setEndDate(LocalDate.now());
                        employeeProjectRepository.save(assignment);
                        return new Success<>(true);
                    }).orElseGet(() ->
                            new Failure<>("Employee project assignment not found for employee: " + employeeId));
        } catch (Exception e) {
            return new Failure<>("Failed to assign project: " + e.getMessage());
        }
    }

    public Result<List<Project>, String> getAvailableProjectForEmployee(Long employeeId) {
        try {
            List<Project> projects = projectRepository.findAvailableProjectsForEmployee(employeeId);
            return new Success<>(projects);
        } catch (Exception e) {
            return new Failure<>("Failed to retrieve available projects: " + e.getMessage());
        }

    }
}
