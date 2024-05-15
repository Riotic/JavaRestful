package com.quest.etna.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    private final UserRepository repository;

    public UserDetailsServiceImp(UserRepository repository){
        this.repository = repository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    
        return user;
    }
}
