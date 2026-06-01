package com.siddh.EventRegistrationSystem.repository;

import com.siddh.EventRegistrationSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsByUserName(String userName);
    public User findByUserName(String userName);
}
