package com.maider.erp.web.controllers;

import com.maider.erp.domain.entities.Client;
import com.maider.erp.domain.entities.Employee;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import com.maider.erp.domain.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public String getAll(Model model) {
        Result<List<Client>, String> result = clientService.getAll();
        if (result instanceof Success<?, ?>) {
            model.addAttribute("clients", result.getValue());
        } else {
            model.addAttribute("error", result.getError());
        }
        return "clients";
    }

    @GetMapping("/clients/new")
    public String showCreateClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "new_client";
    }

    @PostMapping("/clients/create")
    public String createClient(@RequestParam String name,
                               @RequestParam String companyName,
                               RedirectAttributes redirectAttributes) {

        Result<Client, String> result = clientService.createClient(name, companyName);

        if (result instanceof Success<?, ?>) {
            redirectAttributes.addFlashAttribute("success", "Client created successfully.");
            return "redirect:/clients";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getError());
            return "redirect:/clients/new";
        }
    }

    @GetMapping("/clients/{id}")
    public String getClientsDetails(@PathVariable Long id, Model model,
                                     RedirectAttributes redirectAttributes) {
        Result<Client, String> result = clientService.getById(id);
        if (result instanceof Success<?, ?>) {
            model.addAttribute("client", result.getValue());
            return "client_detail";
        } else {
            redirectAttributes.addFlashAttribute("error", result.getError());
            return "redirect:/clients";
        }
    }
}
