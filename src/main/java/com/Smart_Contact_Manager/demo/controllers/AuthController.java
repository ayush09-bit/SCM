package com.Smart_Contact_Manager.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Smart_Contact_Manager.demo.repositories.UserRepopsitory;
import com.Smart_Contact_Manager.demo.users.User;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepopsitory userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
        @RequestParam("token") String token
    )
    {
        User user = userRepo.findByEmailToken(token).orElse(null);
        
        if(user != null)
        {
            if(user.getEmailToken().equals(token)){

                user.setEmailverified(true);
                user.setEnabled(true);
                userRepo.save(user);
            }

            return "success_page";

        }

        return "error_page";

    }


}
