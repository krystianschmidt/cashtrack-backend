package de.dhbw.plugins.rest.security;

import de.dhbw.cleanproject.adapter.user.UserToUserResourceMapper;
import de.dhbw.cleanproject.adapter.user.UserResource;
import de.dhbw.cleanproject.domain.user.User;
import de.dhbw.cleanproject.domain.user.UserApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserApplication userApplication;
    private final UserToUserResourceMapper userToUserResourceMapper;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User registerUser){
        userApplication.createUser(registerUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/iAm")
    public ResponseEntity<?> getUser(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResource user = userToUserResourceMapper.apply(principal);

        return ResponseEntity.ok(user);
    }

}

