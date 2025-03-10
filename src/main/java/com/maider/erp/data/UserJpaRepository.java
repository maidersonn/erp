package com.maider.erp.data;

import com.maider.erp.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}
