package com.quest.etna.model;

public class UserDetails {

    private Integer id;
    private String username;
    private User.UserRole role;

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

    public User.UserRole getRole() {
        return this.role;
    }

    public void setRole(User.UserRole role) {
        this.role = role;
    }

    
}