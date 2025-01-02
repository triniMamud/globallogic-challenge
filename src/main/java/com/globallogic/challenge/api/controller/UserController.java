package com.globallogic.challenge.api.controller;

import com.globallogic.challenge.api.dto.UserDto;
import com.globallogic.challenge.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String token) {
        return userService.login(token);
    }
}
