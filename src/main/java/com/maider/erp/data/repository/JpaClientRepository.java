package com.maider.erp.data.repository;

import com.maider.erp.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaClientRepository extends JpaRepository<Client, Long> {
}
