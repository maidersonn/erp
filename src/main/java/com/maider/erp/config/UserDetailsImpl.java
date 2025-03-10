package com.maider.erp.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.maider.erp.domain.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserDetailsImpl implements UserDetails {

    final private Long id;
    final private String username;
    @JsonIgnore
    final private String password;

    final private Collection<? extends GrantedAuthority> roles;

    public UserDetailsImpl(Long id, String username, String password, String[] roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = AuthorityUtils.createAuthorityList(roles);
    }

    public static UserDetailsImpl build(User user) {
        String[] rolesWithPrefix = Arrays.stream(user.getRoles())
                .map(role -> "ROLE_" + role)
                .toArray(String[]::new);
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                rolesWithPrefix
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
