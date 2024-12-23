package tn.esprit.ashgrid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.ashgrid.dto.LoginRequest;
import tn.esprit.ashgrid.dto.SignupRequest;
import tn.esprit.ashgrid.services.UserService;
import tn.esprit.ashgrid.utils.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
     UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(token);
    }
}

