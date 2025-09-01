 package com.Smart_Contact_Manager.demo.controllers;

import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;



@Controller
public class PageController {
    @GetMapping("/home")
    public String home(Model model){

        System.out.println("home pagge handler");
        //semding data to view
        // model.addAttribute("name","Ayush chaudhary"); // this data can be accessed/send to htmk page
        // model.addAttribute("age", 12);
        // model.addAttribute("link","https://github.com/ayush09-bit/ProductTrackerWeb"); 
        return "home";
    }

    @GetMapping("/about")
    public String aboutpage()
    {
        System.out.println("about page loading");
        return "about";
     }

    @GetMapping("/services")
    public String servicespage()
    {
        System.out.println("services page loading");
        return "services";
    }

}
