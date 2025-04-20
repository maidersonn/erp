package com.maider.erp.web.controllers;

import com.maider.erp.domain.entities.Employee;
import com.maider.erp.domain.entities.Project;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import com.maider.erp.domain.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public String getAll(Model model) {
        Result<List<Employee>, String> result = employeeService.getAll();
        if (result instanceof Success<?, ?>) {
            model.addAttribute("employees", result.getValue());
        } else {
            model.addAttribute("error", result.getError());
        }
        return "employees";
    }

    @GetMapping("/employees/new")
    public String showEmployeeForm(Model model) {
        return "new_employee";
    }

    @PostMapping("/employees/create")
    public String createEmployee(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String dni,
                                 @RequestParam String companyRole,
                                 RedirectAttributes redirectAttributes) {
        Result<Employee, String> result = employeeService.createEmployee(firstName, lastName, dni, companyRole);
        if (result instanceof Success<?, ?>) {
            redirectAttributes.addFlashAttribute("success", "Employee created successfully.");
            return "redirect:/employees";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getError());
            return "redirect:/employees/new";
        }
    }

    @GetMapping("/employees/{id}")
    public String getEmployeeDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Result<Employee, String> result = employeeService.getById(id);
        Result<List<Project>, String> availableProjects = employeeService.getAvailableProjectForEmployee(id);
        if (result instanceof Success<?, ?> && availableProjects instanceof Success<?, ?>) {
            model.addAttribute("employee", result.getValue());
            model.addAttribute("availableProjects", availableProjects.getValue());
            return "employee_detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "An error occurred fetching user " + id);
            return "redirect:/employees";
        }
    }

    @GetMapping("/employees/unassign-project/{employeeId}/{projectId}")
    public String unassignProject(@PathVariable Long employeeId,
                                  @PathVariable Long projectId,
                                  RedirectAttributes redirectAttributes) {
       Result<Boolean, String> result = employeeService.unassignProject(employeeId, projectId);
        if (result instanceof Success<?, ?>) {
            redirectAttributes.addFlashAttribute("success", "Project unassigned successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", result.getError());
        }

        return "redirect:/employees/" + employeeId;
    }

    @GetMapping("/employees/terminate/{id}")
    public String terminate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Result<Boolean, String> result = employeeService.terminate(id);
        if (result instanceof Success<?, ?>) {
            redirectAttributes.addFlashAttribute("success", "Employee terminated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", result.getError());
        }
        return "redirect:/employees";
    }

    @GetMapping("/employees/assign-project/{id}")
    public String showAssignProjectForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Result<Employee, String> employeeResult = employeeService.getById(id);
        if (employeeResult instanceof Success<?, ?>) {
            model.addAttribute("employee", employeeResult.getValue());
            // Additional attributes might be needed for the form: list of available projects, roles, etc.
            return "assignProject";
        } else {
            redirectAttributes.addFlashAttribute("error", employeeResult.getError());
            return "redirect:/employees";
        }
    }

    @PostMapping("/employees/assign-project")
    public String assignProjectToEmployee(@RequestParam Long employeeId,
                                          @RequestParam Long projectId,
                                          @RequestParam String projectRole,
                                          RedirectAttributes redirectAttributes) {
        Result<Boolean, String> result = employeeService.assignProject(employeeId, projectId, projectRole);
        if (result instanceof Success) {
            redirectAttributes.addFlashAttribute("success", "Project assigned successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", result.getError());
        }
        return "redirect:/employees/" + employeeId;
    }
}
