package com.example.tute_backend.repository;

import com.example.tute_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);
    User findUserByUsername(String username);

    User findFirstByEmailOrUsername(String email, String username);

}
