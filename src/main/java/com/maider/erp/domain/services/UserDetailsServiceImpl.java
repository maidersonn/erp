package com.maider.erp.domain.services;

import com.maider.erp.config.UserDetailsImpl;
import com.maider.erp.domain.entities.User;
import com.maider.erp.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            UserDetails userDetails = UserDetailsImpl.build(user);
            return userDetails;
        } catch (Exception e){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
    }
}
