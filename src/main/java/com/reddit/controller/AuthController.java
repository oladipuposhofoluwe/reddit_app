package com.reddit.controller;

import com.reddit.dto.AuthenticationResponse;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.RegisterRequestDto;
import com.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    private ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequestDto){
        System.out.println("ENTER SIGN UP ");
        authService.signup(registerRequestDto);
        return new ResponseEntity<>("User Registration Successful. Please check your mail for email verification", HttpStatus.OK);
    }

    @GetMapping("acountVerification/{token}")
    private ResponseEntity<String> accountVerification(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully ", HttpStatus.OK);
    }


    @PostMapping("/login")
    private AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        System.out.println("LOGGED IN");
           return this.authService.login(loginRequest);
    }
}
