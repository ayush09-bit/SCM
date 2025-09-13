
package com.Smart_Contact_Manager.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user")
public class UserController {
 

    //user dashboard page
    @RequestMapping(value = "/dashboard", 
    method = {RequestMethod.GET, RequestMethod.POST})
    public String dashboard() {
    return "user/dashboard";
    }

    //user profile page
     @GetMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }
    


    //user add conttact page

    //user view contact

    //user edit contact

    //user delete contact

    //user search contact
}
