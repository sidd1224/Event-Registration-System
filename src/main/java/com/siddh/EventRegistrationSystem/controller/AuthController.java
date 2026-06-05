package com.siddh.EventRegistrationSystem.controller;

import com.siddh.EventRegistrationSystem.dto.AuthRequest;
import com.siddh.EventRegistrationSystem.dto.AuthResponse;
import com.siddh.EventRegistrationSystem.dto.RefreshRequest;
import com.siddh.EventRegistrationSystem.entity.RefreshToken;
import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.repository.RefreshTokenRepository;
import com.siddh.EventRegistrationSystem.repository.UserRepository;
import com.siddh.EventRegistrationSystem.service.JwtAuthService;
import com.siddh.EventRegistrationSystem.service.RefreshTokenService;
import com.siddh.EventRegistrationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  private final AuthenticationManager authenticationManager;

  private final JwtAuthService jwtAuthService;

  private final RefreshTokenService refreshTokenService;

  private final UserRepository userRepository;

  private final RefreshTokenRepository refreshTokenRepository;


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        boolean userCreated=userService.createUser(user);
        if(userCreated){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
    @PostMapping("/login")
    @Transactional
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
         Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                 authRequest.getUserName(),
                 authRequest.getPassword()
         )
         );
         User authUser=userRepository.findByUserName(auth.getName());
         refreshTokenService.revokeAllTokens(authUser);
        String refreshToken=refreshTokenService.createRefreshToken(authUser).getToken();
        String accessToken=jwtAuthService.generateToken(authUser.getUserName());

         return new ResponseEntity<>(new AuthResponse(accessToken,refreshToken) ,HttpStatus.ACCEPTED);

    }
    @PostMapping("/refresh")
    @Transactional
    public  ResponseEntity<?> refreshRequest(@RequestBody RefreshRequest refreshToken){
            RefreshToken validatedRefreshToken = refreshTokenService.validateRefreshToken(refreshToken.getRefreshToken());
            if (validatedRefreshToken!=null){
                refreshTokenService.revokeTokens(validatedRefreshToken.getToken());
                String newRefreshToken = refreshTokenService.createRefreshToken((validatedRefreshToken.getUser())).getToken();
                String accessToken = jwtAuthService.generateToken((validatedRefreshToken.getUser().getUserName()));
                return new ResponseEntity<>(new AuthResponse(accessToken, newRefreshToken), HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("Token Not Found:", HttpStatus.NOT_ACCEPTABLE);
        }
}
