package com.Smart_Contact_Manager.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Smart_Contact_Manager.demo.helper.Helper;
import com.Smart_Contact_Manager.demo.services.UserServices;
import com.Smart_Contact_Manager.demo.users.User;

@ControllerAdvice
public class RootController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private  UserServices userServices;




    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication)
    {
        if(authentication == null) return;
       String username = Helper.getEmailOfLoggedInUser(authentication);
    
        logger.info("user logged in name : {}" , username);
        User user = userServices.getUserByEmail(username);

        // get user from database
        System.out.println(user.getName());
        System.out.println(user.getEmail());
        model.addAttribute("loggedinuser", user); //(saves value of logged in user, gives value to the model to store values)

        
    }

}
