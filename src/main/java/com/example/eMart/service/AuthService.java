package com.example.eMart.service;

import com.example.eMart.dto.AuthRequestDTO;
import com.example.eMart.entity.Role;
import com.example.eMart.entity.User;
import com.example.eMart.repository.RoleRepository;
import com.example.eMart.repository.UserRepository;
import com.example.eMart.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.roleRepo = roleRepo;
    }

    // ✅ REGISTER
    public String register(String username, String password){

        if(userRepo.findByUsername(username).isPresent()){
            throw new RuntimeException("Username is already in use");
        }

        // Get or create ROLE_USER
        Role role = roleRepo.findByName("ROLE_USER")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_USER")));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepo.save(user);

        return "User registered successfully";
    }

    // ✅ LOGIN (returns JWT token)
    public String login(AuthRequestDTO dto){

        User user = userRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}