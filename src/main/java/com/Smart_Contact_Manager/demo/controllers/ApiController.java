package com.Smart_Contact_Manager.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Smart_Contact_Manager.demo.services.ContactService;
import com.Smart_Contact_Manager.demo.users.Contact;

@RestController
@RequestMapping("/api")
public class ApiController {

    // get contacts of user

    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId)
    {
        return contactService.getById(contactId);
        
    }



}
