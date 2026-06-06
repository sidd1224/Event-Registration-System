package com.siddh.EventRegistrationSystem.service;


import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User createAdmin(User user) {
        boolean userInDb = userRepository.existsByUserName(user.getUserName());
        if (!userInDb) {
            user.getRoles().addAll(List.of("ATTENDEE","ADMIN"));
            String encodedPassword=passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public User updateUserToAdmin(User user) {
        User userInDb = userRepository.findByUserName(user.getUserName());
        if (userInDb!=null) {
            userInDb.getRoles().add("ADMIN");
            userRepository.save(userInDb);
            return userInDb;
        }
        throw new RuntimeException("username not found:"+user.getUserName());
    }
    public User updateAdminToUser(User user) {
        User userInDb = userRepository.findByUserName(user.getUserName());
        if (userInDb!=null) {
            userInDb.getRoles().remove("ADMIN");
            userRepository.save(userInDb);
            return userInDb;
        }
        throw new RuntimeException("username not found:"+user.getUserName());
    }

    public User deleteAdmin(User user) {
        User userInDb = userRepository.findByUserName(user.getUserName());
        if (userInDb!=null) {
            userRepository.delete(userInDb);
            return userInDb;
        }
        throw new RuntimeException("username not found:"+user.getUserName());
    }
}
