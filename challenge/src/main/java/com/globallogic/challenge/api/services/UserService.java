package com.globallogic.challenge.api.services;

import com.globallogic.challenge.api.dao.User;
import com.globallogic.challenge.api.dto.UserDto;
import com.globallogic.challenge.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.Optional;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static java.time.LocalDateTime.*;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> signUp(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User user = new User();
        user.setId(randomUUID());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCreated(now());
        user.setLastLogin(now());
        user.setToken(generateToken(user.getEmail()));
        user.setActive(true);

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> login(String token) {
        String email = parseToken(token);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        User user = userOptional.get();
        user.setLastLogin(now());
        user.setToken(generateToken(user.getEmail()));
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    public String generateToken(String email) {
        return tokenService.generateToken(email);
    }

    private String parseToken(String token) {
        return tokenService.parseToken(token);
    }
}
