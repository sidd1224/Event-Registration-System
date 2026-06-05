package com.siddh.EventRegistrationSystem.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
