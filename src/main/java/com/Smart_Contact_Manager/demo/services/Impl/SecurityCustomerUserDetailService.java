package com.Smart_Contact_Manager.demo.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Smart_Contact_Manager.demo.repositories.UserRepopsitory;

@Service
public class SecurityCustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepopsitory userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //Load user

        return userRepo.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("username not found with this email" + username));
    }

}
