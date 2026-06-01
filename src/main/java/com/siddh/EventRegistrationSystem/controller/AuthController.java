package com.siddh.EventRegistrationSystem.controller;

import com.siddh.EventRegistrationSystem.dto.AuthRequest;
import com.siddh.EventRegistrationSystem.dto.AuthResponse;
import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.service.JwtAuthService;
import com.siddh.EventRegistrationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final JwtAuthService jwtAuthService;


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        boolean userCreated=userService.createUser(user);
        if(userCreated){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
         Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 authRequest.getUserName(),
                 authRequest.getPassword()
         )
         );
         String token=jwtAuthService.generateToken(auth.getName());
         return new ResponseEntity<>(new AuthResponse(token) ,HttpStatus.ACCEPTED);

    }
}
