package com.siddh.EventRegistrationSystem.service;

import com.siddh.EventRegistrationSystem.dto.AuthRequest;
import com.siddh.EventRegistrationSystem.dto.AuthResponse;
import com.siddh.EventRegistrationSystem.dto.RefreshRequest;
import com.siddh.EventRegistrationSystem.entity.RefreshToken;
import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RefreshTokenService refreshTokenService;

    private final JwtAuthService jwtAuthService;

    private final PasswordEncoder passwordEncoder;


    @Transactional

    public AuthResponse userLogin(AuthRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),
                        authRequest.getPassword()
                )
        );
        User authUser = userRepository.findByUserName(auth.getName());
        refreshTokenService.revokeAllTokens(authUser);
        String refreshToken = refreshTokenService.createRefreshToken(authUser).getToken();
        String accessToken = jwtAuthService.generateToken(authUser.getUserName());
        return new AuthResponse(accessToken, refreshToken);
    }

    public boolean userRegister(User user) {
        boolean userInDb = userRepository.existsByUserName(user.getUserName());
        if (!userInDb) {
            user.getRoles().add("ATTENDEE");
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public AuthResponse userRefresh(RefreshRequest refreshRequest) {
        RefreshToken validatedRefreshToken = refreshTokenService.validateRefreshToken(refreshRequest.getRefreshToken());
        if (validatedRefreshToken != null) {
            refreshTokenService.revokeTokens(validatedRefreshToken.getToken());
            String newRefreshToken = refreshTokenService.createRefreshToken((validatedRefreshToken.getUser())).getToken();
            String accessToken = jwtAuthService.generateToken((validatedRefreshToken.getUser().getUserName()));
            return new AuthResponse(accessToken, newRefreshToken);
        }
        throw new IllegalArgumentException("Refresh Token Not found");
    }
}
