package com.siddh.EventRegistrationSystem.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@RequiredArgsConstructor
public class AuthResponse {
    private final String token;
}
