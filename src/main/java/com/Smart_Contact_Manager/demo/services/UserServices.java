package com.Smart_Contact_Manager.demo.services;

import java.util.List;
import java.util.Optional;

import com.Smart_Contact_Manager.demo.users.User;

public interface UserServices {
    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);
    List<User> getAllUser();

}
