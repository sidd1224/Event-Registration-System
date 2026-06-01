package com.siddh.EventRegistrationSystem.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class AuthRequest {
    private String userName;
    private String password;
}
