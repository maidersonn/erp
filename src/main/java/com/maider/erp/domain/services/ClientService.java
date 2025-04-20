package com.maider.erp.domain.services;

import com.maider.erp.domain.entities.Client;
import com.maider.erp.domain.entities.Project;
import com.maider.erp.domain.repositories.ClientRepository;
import com.maider.erp.domain.repositories.ProjectRepository;
import com.maider.erp.domain.result.Failure;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Result<List<Client>, String> getAll() {
        try {
            List<Client> clients = clientRepository.findAll();
            return new Success<>(clients);
        } catch (Exception e) {
            return new Failure<>("Failed to fetch clients: " + e.getMessage());
        }
    }

    public Result<Client, String> getById(Long id) {
        return clientRepository.findById(id)
                .<Result<Client, String>>map(Success::new)
                .orElseGet(() -> new Failure<>("Project with ID " + id + " not found."));
    }

    public Result<Client, String> createClient(String name, String companyName) {

        if (name == null || name.trim().isEmpty()) {
            return new Failure<>("Client name is required.");
        }
        if (companyName == null || companyName.trim().isEmpty()) {
            return new Failure<>("Company name is required.");
        }

        Client client = new Client();
        client.setName(name.trim());
        client.setCompanyName(companyName.trim());
        try {
            return new Success<>(clientRepository.save(client));
        } catch (Exception e) {
            return new Failure<>("Error creating client: " + e.getMessage());
        }


    }
}
