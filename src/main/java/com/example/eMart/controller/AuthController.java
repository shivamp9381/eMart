package com.example.eMart.controller;

import com.example.eMart.dto.AuthRequestDTO;
import com.example.eMart.dto.RegisterDTO;
import com.example.eMart.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO dto){
        return ResponseEntity.ok(
                authService.register(dto.getUsername(), dto.getPassword())
        );
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO dto){
        return ResponseEntity.ok(authService.login(dto));
    }
}