package com.Smart_Contact_Manager.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Smart_Contact_Manager.demo.users.User;

public interface UserRepopsitory extends JpaRepository<User, String> //<with which entity you are working , type of your indentity>
 {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailToken(String emailToken);


}


