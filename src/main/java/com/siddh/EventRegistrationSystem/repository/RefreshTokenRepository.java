package com.siddh.EventRegistrationSystem.repository;

import com.siddh.EventRegistrationSystem.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    public RefreshToken findByToken(String token);
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.revoked=true WHERE rt.user.id= :id AND rt.revoked=false")
    public int revokeAllTokens(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.revoked=true WHERE rt.token= :token AND rt.revoked=false")
    public int revokeByToken(@Param("token") String token);


}
