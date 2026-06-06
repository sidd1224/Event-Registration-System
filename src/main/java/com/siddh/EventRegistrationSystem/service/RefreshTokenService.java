package com.siddh.EventRegistrationSystem.service;

import com.siddh.EventRegistrationSystem.entity.RefreshToken;
import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.repository.RefreshTokenRepository;
import com.siddh.EventRegistrationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken= RefreshToken.builder().token(UUID.randomUUID().toString())
                .expiry(Instant.now().plus(Duration.ofDays(30)))
                .revoked(false)
                .user(user)
                .build();
        refreshTokenRepository.save(refreshToken);
        log.info("Refresh Token created and saved successfully in the db for the user:{}",user.getUserName());
        return refreshToken;
    }

    public void revokeAllTokens(User user) {
        try {
            refreshTokenRepository.revokeAllTokens(user.getId());
            log.info("Refresh Tokens revoked for User {}", user.getUserName());
            return;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to revoke token for the user"+ user.getUserName());
        }
    }
    public void revokeTokens(String token) {
        try {
            refreshTokenRepository.revokeByToken(token);
            log.info("Refresh Tokens revoked :{}", token);
            return;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to revoke token :"+ token);
        }
    }

    public RefreshToken validateRefreshToken(String token){
        RefreshToken refreshToken=refreshTokenRepository.findByToken(token);

        if(refreshToken==null){
            throw new RuntimeException("Refresh token Not found ");
        }
        if(refreshToken.getRevoked()) {
            revokeAllTokens(refreshToken.getUser());
            throw new IllegalArgumentException("Refresh Token Reuse");
        }
        Instant expiry=refreshToken.getExpiry();
        if(expiry.isBefore(Instant.now())){
            throw new RuntimeException("Refresh Token Expired");
            }
        return refreshToken;
    }

}
