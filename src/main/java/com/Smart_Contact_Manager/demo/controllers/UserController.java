
package com.Smart_Contact_Manager.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {
 

    //user dashboard page
    @PostMapping("/dashboard")
    public String userDashboard() {
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
