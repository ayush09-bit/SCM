 package com.Smart_Contact_Manager.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Smart_Contact_Manager.demo.helper.Message;
import com.Smart_Contact_Manager.demo.helper.MessageType;
import com.Smart_Contact_Manager.demo.services.UserServices;
import com.Smart_Contact_Manager.demo.users.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


@Controller
public class PageController {

    @Autowired
    private UserServices userService;

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

     @GetMapping("/contact")
    public String contactpage()
    {
        System.out.println("contact page loading");
        return "contact";
    }

     @GetMapping("/login")
    public String loginpage()
    {
        System.out.println("login page loading");
        return "login";
    }

     @GetMapping("/register")
    public String signuppage(Model model)
    {
        System.out.println("Register page loading");

        UserForm userform = new UserForm();
        // sending default value to form
        //userform.setName("Ayush");
        model.addAttribute("userdata", userform);
        return "register";
    }

    //proceesing register
    @PostMapping("/do-register")
    public String processRegister(@Valid  @ModelAttribute("userdata") UserForm userdata,BindingResult rBindingResult ,HttpSession session)
    {
        System.out.println("Processing Registration");
        //fetch form data
        //userform --> user
       
        User user = new User();
        user.setName(userdata.getName());
        user.setEmail(userdata.getEmail());
        user.setPhonenumber(userdata.getPhonenumber());
        user.setPassword(userdata.getPassword());
        user.setAbout(userdata.getAbout());
        user.setProfilepic("https://www.google.com/search?sca_esv=0ba2932d10fe8916&udm=2&fbs=AIIjpHxU7SXXniUZfeShr2fp4giZ1Y6MJ25_tmWITc7uy4KIemkjk18Cn72Gp24fGkjjh6yoZyhlDvfYYroN834IkBBoW9T-W8pUltI7zOQsVGtT6W3-fOQqJRRIWvecOfco6Uomryd9fNTXSzVf5jYZhVNPyXjyfFWcHFvBKuZk9bpAkoxfyYoS51lwH2fpJuyxfszt3UO4YxCgREDdhFugGAAZ29Z6pg&q=profile+pic&sa=X&ved=2ahUKEwjaysLWiMmPAxXBbmwGHVuNJ-IQtKgLegQIExAB&biw=767&bih=730&dpr=1.25#vhid=jKIiF0rAa_qOjM&vssid=mosaic");
        //validate form data
        if(rBindingResult.hasErrors())
        {
            return "register";
        }

        //save to database 

        User savedUser = userService.saveUser(user);
        //message = successsfull registerd
        //return msg
 
        Message message = Message.builder()
            .content("Registration successfull")
            .type(MessageType.green)
            .build();

        session.setAttribute("message", message);

        System.out.println("user saved" + savedUser);

        return "redirect:/register";

    }

}
