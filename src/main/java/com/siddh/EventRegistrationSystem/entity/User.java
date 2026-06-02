package com.siddh.EventRegistrationSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(unique = true)
    private String userName;
    @NonNull
    private String password;
    @ElementCollection
    private List<String> roles =new ArrayList<>();
}
