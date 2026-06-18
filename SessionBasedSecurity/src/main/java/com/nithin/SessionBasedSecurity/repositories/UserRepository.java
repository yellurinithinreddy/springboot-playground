package com.nithin.SessionBasedSecurity.repositories;

import com.nithin.SessionBasedSecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);
}
