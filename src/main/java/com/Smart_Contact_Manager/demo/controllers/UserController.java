
package com.Smart_Contact_Manager.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);    

    

    //user dashboard page
    @RequestMapping(value = "/dashboard", 
    method = {RequestMethod.GET, RequestMethod.POST})
    public String dashboard() {
    return "user/dashboard";
    }


    //user profile page
      @RequestMapping(value = "/profile", 
    method = {RequestMethod.GET, RequestMethod.POST})
    public String userProfile(Model model, Authentication authentication) {
       
    return "user/profile";
    }
    


    //user add conttact page

    //user view contact

    //user edit contact

    //user delete contact

    //user search contact
}
