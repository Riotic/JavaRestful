package com.quest.etna.controller;

import com.quest.etna.DTO.UserDTO;
import com.quest.etna.config.JwtTokenUtil;
import com.quest.etna.DTO.ErrorResponseDTO;
import com.quest.etna.DTO.ResponseDTO;
import com.quest.etna.model.User;
import com.quest.etna.model.UserDetail;
import com.quest.etna.model.UserRole;
import com.quest.etna.repositories.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
public class AuthenticationController {
     private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private String token;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtService;

    public AuthenticationController(UserRepository userRepository, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = new JwtTokenUtil();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        boolean errorPresent = false;
        List<String> errors = new ArrayList<>();
        if( user.username()  == null){
            errors.add("Le champ username est vide.");
            errorPresent = true;
        };
        if( user.password() == null){
            errors.add("Le champ password est vide.");
            errorPresent = true;
        }else if(user.password().trim().isEmpty()){
            errors.add("Le champ password ne peut pas être composé uniquement d'espaces.");
            errorPresent = true;
        };

        if(errorPresent){
            return new ResponseEntity<>(
                new ErrorResponseDTO<List<String>>(
                    errors, 
                    400, 
                    HttpStatus.BAD_REQUEST.getReasonPhrase(), 
                    "/register"
                ), 
                HttpStatus.BAD_REQUEST
            );
        }

        if(userRepository.findByUsername(user.username()).isPresent()){
            return new ResponseEntity<>(
                new ErrorResponseDTO<String>(
                    "L'utilisateur existe déjà.", 
                    409, 
                    HttpStatus.CONFLICT.getReasonPhrase(), 
                    "/register"
                ), 
                HttpStatus.valueOf(409)
            );
        }
        try{
            User createdUser = userRepository.save (
                new User(
                    user.username().trim(), 
                    passwordEncoder.encode(user.password().trim()),
                    user.role()
                )
            );
            return new ResponseEntity<>(
                new ResponseDTO<UserDetail>(
                    new UserDetail(
                        createdUser.getId(), 
                        createdUser.getUsername(), 
                        createdUser.getRole()
                    ), 
                    201, 
                    HttpStatus.CREATED.getReasonPhrase(), 
                    "/register"
                ), 
                HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(
                new ErrorResponseDTO<String>(
                    "Une erreur est survenue lors de la création de l'utilisateur.", 
                    500, 
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), 
                    "/register"
                ), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO request){
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            Optional<User> user = userRepository.findByUsername(request.username());
            if(user.isPresent()) {
                String token = jwtService.generateToken(request.username());
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                return new ResponseEntity<>(tokenMap, HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            ErrorResponseDTO<String> errorResponse = new ErrorResponseDTO<>(
                "Mauvais nom d'utilisateur ou mot de passe.", 
                401, 
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), 
                "/authenticate"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new ResponseEntity<>(new UserDetail(user.getId(), user.getUsername(), user.getRole()), HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponseDTO<String> errorResponse = new ErrorResponseDTO<>(
                "Problème au niveau du JWT.", 
                401, 
                HttpStatus.UNAUTHORIZED.getReasonPhrase(), 
                "/me"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}