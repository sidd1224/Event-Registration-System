package com.siddh.EventRegistrationSystem.repository;

import com.siddh.EventRegistrationSystem.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    public String findByToken(String token);
    public
}
