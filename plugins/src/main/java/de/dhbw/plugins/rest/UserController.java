package de.dhbw.plugins.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping
    public ResponseEntity<?> createUser(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
