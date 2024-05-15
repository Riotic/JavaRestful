package com.quest.etna.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.DTO.ErrorResponseDTO;
import com.quest.etna.DTO.ResponseDTO;
import com.quest.etna.DTO.UserDTO;
import com.quest.etna.config.exception.NoAccessException;
import com.quest.etna.config.exception.NoRightsException;
import com.quest.etna.config.exception.NotFoundException;
import com.quest.etna.model.User;
import com.quest.etna.model.UserDetail;
import com.quest.etna.model.UserRole;
import com.quest.etna.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(Principal principal) {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            throw new NoAccessException("Vous n'êtes pas connectés ou vous n'avez pas les accès.");
        }

        List<User> users = (List<User>) userRepository.findAll();
        List<UserDetail> userDetails = users.stream()
                .map(user -> new UserDetail(user.getId(), user.getUsername(), user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDetails);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(Principal principal, @PathVariable Long id) {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            throw new NoAccessException("Vous n'êtes pas connectés ou vous n'avez pas les accès.");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (currentUser.getRole().equals(UserRole.ROLE_ADMIN) || user.equals(currentUser)) {
                UserDetail userDetail = new UserDetail(user.getId(), user.getUsername(), user.getRole());
                return ResponseEntity.ok(userDetail);
            } else {
                throw new NoRightsException("Vous n'avez pas les droits requis.");
            }
        } else {
            throw new NotFoundException("Utilisateur inconnu");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(Principal principal, @PathVariable Long id,
            @RequestBody UserDTO updatedUserDTO) {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            throw new NoAccessException("Vous n'êtes pas connectés ou vous n'avez pas les accès.");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (currentUser.getRole().equals(UserRole.ROLE_ADMIN) || user.equals(currentUser)) {
                if (!currentUser.getRole().equals(UserRole.ROLE_ADMIN)) {
                    // If not admin, only update the username
                    user.setUsername(updatedUserDTO.username());
                } else {
                    // If admin, update both username and role
                    user.setUsername(updatedUserDTO.username());
                    user.setRole(updatedUserDTO.role());
                }

                User savedUser = userRepository.save(user);
                UserDetail userDetail = new UserDetail(savedUser.getId(), savedUser.getUsername(), savedUser.getRole());
                return ResponseEntity.ok(userDetail);
            } else {
                throw new NoRightsException("Vous n'avez pas les droits requis.");
            }
        } else {
            throw new NotFoundException("Utilisateur inconnu");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(Principal principal, @PathVariable Long id) {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            throw new NoAccessException("Vous n'êtes pas connectés ou vous n'avez pas les accès.");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (currentUser.getRole().equals(UserRole.ROLE_ADMIN) || user.equals(currentUser)) {
                userRepository.deleteById(id);
                return new ResponseEntity<>(
                    new ResponseDTO<String>(
                        "Utilisateur supprimé avec succès.", 
                        200, 
                        HttpStatus.OK.getReasonPhrase(), 
                        "/user/" + id
                    ), 
                    HttpStatus.OK);
            } else {
                throw new NoRightsException("Vous n'avez pas les droits requis.");
            }
        } else {
            throw new NotFoundException("Utilisateur inconnu");
        }
    }

    private User getCurrentUser(Principal principal) {
        if (principal != null) {
            Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
        }
        return null;
    }
}
