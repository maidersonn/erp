package com.maider.erp.domain.repositories;

import com.maider.erp.domain.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client);
}
