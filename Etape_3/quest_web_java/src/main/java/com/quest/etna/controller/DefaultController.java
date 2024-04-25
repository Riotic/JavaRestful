package com.quest.etna.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping("/testSuccess")
    public ResponseEntity<String> testSuccess() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/testNotFound")
    public ResponseEntity<String> testNotFound() {
        return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/testError")
    public ResponseEntity<String> testError() {
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}