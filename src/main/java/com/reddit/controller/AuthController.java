package com.reddit.controller;

import com.reddit.dto.AuthenticationResponse;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.RefreshTokenRequest;
import com.reddit.dto.RegisterRequestDto;
import com.reddit.service.AuthService;
import com.reddit.service.refreshToken.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("signup")
    private ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequestDto){
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
           return this.authService.login(loginRequest);
    }

    @PostMapping("refresh/token")
    private AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return this.authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    private ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token successfully deleted!!!");
    }
}
