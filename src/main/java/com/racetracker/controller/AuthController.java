package com.racetracker.controller;

import com.racetracker.dto.AuthRequest;
import com.racetracker.model.User;
import com.racetracker.repository.UserRepository;
import com.racetracker.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && user.getPassword().equals(request.getPassword())) {
            return jwtService.generateToken(user.getUsername());
        }
        throw new RuntimeException("Credenciais inválidas");
    }
}
