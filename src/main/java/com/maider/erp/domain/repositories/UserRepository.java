package com.maider.erp.domain.repositories;


import com.maider.erp.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    List<User> findAll();

    Boolean delete(Long id);

    User findByUsername(String username);

    Optional<User> findById(Long id);

    User updateUser(Long id, User user);
}
