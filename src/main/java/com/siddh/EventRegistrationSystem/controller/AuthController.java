package com.siddh.EventRegistrationSystem.controller;

import com.siddh.EventRegistrationSystem.dto.AuthRequest;
import com.siddh.EventRegistrationSystem.dto.AuthResponse;
import com.siddh.EventRegistrationSystem.dto.RefreshRequest;
import com.siddh.EventRegistrationSystem.entity.RefreshToken;
import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.service.AuthService;
import com.siddh.EventRegistrationSystem.service.JwtAuthService;
import com.siddh.EventRegistrationSystem.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtAuthService jwtAuthService;

  private final RefreshTokenService refreshTokenService;

  private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@RequestBody User user){
        boolean userCreated=authService.userRegister(user);
        if(userCreated){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
         AuthResponse authResponse=authService.userLogin(authRequest);
         return new ResponseEntity<>(authResponse,HttpStatus.ACCEPTED);
    }
    @PostMapping("/refresh")
    public  ResponseEntity<?> refreshRequest(@RequestBody RefreshRequest refreshToken){
        AuthResponse authResponse=authService.userRefresh(refreshToken);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }


}
