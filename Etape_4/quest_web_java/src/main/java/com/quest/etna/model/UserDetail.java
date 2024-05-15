package com.quest.etna.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.quest.etna.model.UserRole;

public class UserDetail{

    private Integer id;
    private String username;
    private UserRole role;

    public UserDetail(Integer id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    
}