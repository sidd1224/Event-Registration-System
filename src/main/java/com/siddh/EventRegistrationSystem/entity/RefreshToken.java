package com.siddh.EventRegistrationSystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String token;

    @NonNull
    private Instant expiry;

    @NonNull
    private Boolean revoked;

    @ManyToOne
    private User user;

}
