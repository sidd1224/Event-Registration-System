package com.siddh.EventRegistrationSystem.service;


import com.siddh.EventRegistrationSystem.entity.User;
import com.siddh.EventRegistrationSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        boolean userInDb = userRepository.existsByUserName(user.getUserName());
        if (!userInDb) {
            user.getRoles().add("ATTENDEE");
            String encodedPassword=passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
