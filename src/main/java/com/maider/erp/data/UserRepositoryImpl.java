package com.maider.erp.data;

import com.maider.erp.data.repository.JpaUserRepository;
import com.maider.erp.domain.entities.User;
import com.maider.erp.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JpaUserRepository jpaRepo;

    @Override
    public User save(User user) {
        return jpaRepo.save(user);
    }

    @Override
    public List<User> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        jpaRepo.deleteById(id.toString());
        return Boolean.TRUE;
    }

    @Override
    public User findByUsername(String username) {
        return jpaRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepo.findById(id.toString());
    }

    @Override
    public User updateUser(Long id, User user) {
        User dbUser = jpaRepo.findById(id.toString()).orElseThrow(() -> new RuntimeException("User not found"));
        dbUser.setUsername(user.getUsername());
        return jpaRepo.save(dbUser);
    }
}
