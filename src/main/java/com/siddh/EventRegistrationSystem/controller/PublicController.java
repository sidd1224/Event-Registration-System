package com.siddh.EventRegistrationSystem.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {


    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }


}
