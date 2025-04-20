package com.maider.erp.data;

import com.maider.erp.data.repository.JpaClientRepository;
import com.maider.erp.domain.entities.Client;
import com.maider.erp.domain.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    @Autowired
    private JpaClientRepository jpaRepo;

    @Override
    public List<Client> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public Client save(Client client) {
        return jpaRepo.save(client);
    }
}
