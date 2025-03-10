package com.maider.erp.domain.services;

import com.maider.erp.domain.entities.User;
import com.maider.erp.domain.repositories.UserRepository;
import com.maider.erp.domain.result.Failure;
import com.maider.erp.domain.result.Result;
import com.maider.erp.domain.result.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Result<User, String> createUser(User user) {
        try {
            String passwordEncrypted = passwordEncoder.encode(user.getPassword());
            User newUser = new User(user.getUsername(), passwordEncrypted, user.getRoles());
            User userCreated = userRepository.save(newUser);
            return new Success<>(userCreated);
        } catch (DataIntegrityViolationException e) {
            return new Failure<>("The name is already taken");
        } catch (Exception e) {
            return new Failure<>("Error relationed with database");
        }
    }

    public Result<List<User>, String> getAll() {
        return new Success<>(userRepository.findAll());
    }

    public Result<Boolean, String> deleteById(Long id) {
        return new Success<>(userRepository.delete(id));
    }

    public Result<User, String> getUserById(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return new Success<>(user);
        } catch (Exception e) {
            return new Failure<>("Error fetching user: " + e.getMessage());
        }
    }

    public Result<User, String> updateUser(Long id, User updatedUser) {
        return new Success<>(userRepository.updateUser(id, updatedUser));
    }
}
