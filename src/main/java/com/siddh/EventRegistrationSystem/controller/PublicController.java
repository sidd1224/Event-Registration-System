package com.siddh.EventRegistrationSystem.controller;
import com.siddh.EventRegistrationSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {

    private final UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

}
