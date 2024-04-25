package com.quest.etna.controller;

import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getPassword() != null && userRepository.findByUsername(user.getUsername()) != null ) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cet utilisateur existe déjà");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        try {
            User savedUser = userRepository.save(user);
            UserDetails userDetails = new UserDetails();
            userDetails.setId(savedUser.getId());
            userDetails.setUsername(savedUser.getUsername());
            userDetails.setRole(savedUser.getRole());
            return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Un champ n'est pas rempli ou mal rempli.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}