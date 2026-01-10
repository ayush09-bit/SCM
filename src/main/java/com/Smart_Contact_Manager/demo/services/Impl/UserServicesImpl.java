package com.Smart_Contact_Manager.demo.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Smart_Contact_Manager.demo.helper.AppConstants;
import com.Smart_Contact_Manager.demo.helper.Helper;
import com.Smart_Contact_Manager.demo.helper.ResourceNotFoundException;
import com.Smart_Contact_Manager.demo.repositories.UserRepopsitory;
import com.Smart_Contact_Manager.demo.services.EmailService;
import com.Smart_Contact_Manager.demo.services.UserServices;
import com.Smart_Contact_Manager.demo.users.User;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepopsitory userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {

    String userId = UUID.randomUUID().toString();
    user.setUserId(userId);

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoleList(List.of(AppConstants.ROLE_USER));

    String emailToken = UUID.randomUUID().toString();
    user.setEmailToken(emailToken);

    User savedUser = userRepo.save(user);

    String emailLink = Helper.getLinkForEmailVerification(emailToken);

    try {
        emailService.sendEmail(
            savedUser.getEmail(),
            "Verify Account",
            emailLink
        );
    } catch (Exception e) {
        logger.error("Email sending failed (production safe): {}", e.getMessage());
    }

    return savedUser;
}


    @Override
    public Optional<User> getUserById(String id) {
       return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        //bring user data from databse and stores it in user2 variable 
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));

        //set data to user by user2

        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setAbout(user.getAbout());
        user2.setProfilepic(user.getProfilepic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailverified(user.isEmailverified());
        user2.setPhoneverified(user.isPhoneverified());
        user2.setProvider(user.getProvider());
        user2.setProvideruserId(user.getProvideruserId());

        //save the user to data base

        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
       User user2 = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }
    
    @Override
    public boolean isUserExistByEmail(String email) {
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
       return userRepo.findByEmail(email).orElse(null);
    }
}
