
package com.Smart_Contact_Manager.demo.controllers;

import java.security.Principal;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Smart_Contact_Manager.demo.form.ContactSearchForm;
import com.Smart_Contact_Manager.demo.helper.AppConstants;
import com.Smart_Contact_Manager.demo.helper.Helper;
import com.Smart_Contact_Manager.demo.services.ContactService;
import com.Smart_Contact_Manager.demo.services.UserServices;
import com.Smart_Contact_Manager.demo.users.Contact;
import com.Smart_Contact_Manager.demo.users.User;


@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);    

    @Autowired
    private UserServices userServices;

    @Autowired
    private ContactService contactService;

    //user dashboard page
  @RequestMapping("/dashboard")
public String dashboard(@ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value="page",defaultValue = "0") int page,
        @RequestParam(value="size",defaultValue = AppConstants.PAGE_SIZE +"") int size,
        @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction,
        Model model , Authentication authentication)
    {
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userServices.getUserByEmail(username);

        Page<Contact> Contacts = contactService.getByUser(user,page,size,sortBy,direction);
        
        model.addAttribute("pageContact", Contacts);
        

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
