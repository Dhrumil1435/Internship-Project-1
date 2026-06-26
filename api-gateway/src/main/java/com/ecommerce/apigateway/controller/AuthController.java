package com.ecommerce.apigateway.controller;

import com.ecommerce.apigateway.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // PLACEHOLDER credential check — replace with a real user lookup later
        if ("admin".equals(request.username()) && "password123".equals(request.password())) {
            return jwtUtil.generateToken(request.username());
        }
        throw new RuntimeException("Invalid credentials");
    }

    public record LoginRequest(String username, String password) {}
}