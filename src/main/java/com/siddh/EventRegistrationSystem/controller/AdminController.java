package com.siddh.EventRegistrationSystem.controller;

import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {



    private final AdminService adminService;

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        User userCreated = adminService.createAdmin(user);
//        if (userCreated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/update-to-admin")
    public ResponseEntity<?> updateUserToAdmin(@RequestBody User user) {
        User updatedRole = adminService.updateUserToAdmin(user);
//        if (userCreated) {
            return new ResponseEntity<>(updatedRole,HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/update-to-user")
    public ResponseEntity<?> updateAdminToUser(@RequestBody User user) {
        User updatedRole = adminService.updateAdminToUser(user);
//        if (userCreated) {
            return new ResponseEntity<>(updatedRole,HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
    @PostMapping("/delete-admin")
    public ResponseEntity<?> deleteAdmin(@RequestBody User user) {
        User userDeleted = adminService.deleteAdmin(user);
//        if (userCreated) {
        return new ResponseEntity<>(userDeleted,  HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}
