package com.Smart_Contact_Manager.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.Smart_Contact_Manager.demo.helper.Message;
import com.Smart_Contact_Manager.demo.helper.MessageType;
import com.Smart_Contact_Manager.demo.repositories.UserRepopsitory;
import com.Smart_Contact_Manager.demo.users.User;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepopsitory userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
        @RequestParam("token") String token,
        HttpSession session
        
    )
    {
        User user = userRepo.findByEmailToken(token).orElse(null);
        
        if(user != null)
        {
            if(user.getEmailToken().equals(token)){

                user.setEmailverified(true);
                user.setEnabled(true);
                userRepo.save(user);

                session.setAttribute("message", Message.builder().type(MessageType.green).content("your email is verified now you can login").build());
            }

            return "redirect:/login";

        }

        session.setAttribute("message", Message.builder().type(MessageType.red).content("your email is not verified : Register again").build());

        return "redirect:/register";

    }


}
