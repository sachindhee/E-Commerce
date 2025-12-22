package com.globalkart.Repository;

import com.globalkart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String name);
   // Optional<User> findById( Long id);
}
