package com.Smart_Contact_Manager.demo.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Smart_Contact_Manager.demo.form.ContactForm;
import com.Smart_Contact_Manager.demo.form.ContactSearchForm;
import com.Smart_Contact_Manager.demo.helper.AppConstants;
import com.Smart_Contact_Manager.demo.helper.Helper;
import com.Smart_Contact_Manager.demo.helper.Message;
import com.Smart_Contact_Manager.demo.helper.MessageType;
import com.Smart_Contact_Manager.demo.services.ContactService;
import com.Smart_Contact_Manager.demo.services.UserServices;
import com.Smart_Contact_Manager.demo.services.imageService;
import com.Smart_Contact_Manager.demo.users.Contact;
import com.Smart_Contact_Manager.demo.users.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private imageService imageService;


    @RequestMapping(value="/add" )
    public String addContactView(Model model)
    {
        ContactForm contactForm = new ContactForm();
        // contactForm.setFavourite(true);
        // contactForm.setName("Ayush chaudhary");
        model.addAttribute("contactform", contactForm);


        return "user/add_contact"; 
    }

    @RequestMapping(value="/add" , method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute("contactform") ContactForm contactForm ,BindingResult result , Authentication authentication, HttpSession session)
    {

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userServices.getUserByEmail(username);

        Contact contact = new Contact();

        if(result.hasErrors())
        {   
            session.setAttribute("message", 
            Message.builder()
            .content("Error in adding contact")
            .type(MessageType.red)
            .build()
            );

            return "user/add_contact";
        }

        logger.info("contact image is {}", contactForm.getContactImage().getOriginalFilename());

               

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setFavourite(contactForm.isFavourite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedInlink());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setWebsite(contactForm.getWebsite());

         if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty())
        {
            String fileName = UUID.randomUUID().toString();
            String imgeUrl = imageService.uploadImage(contactForm.getContactImage(),fileName);
            contact.setCloudinaryPicPublicId(fileName);
            contact.setPicture(imgeUrl);
            contactForm.setPicture(imgeUrl);
        }
        contact.setUser(user);

       contactService.save(contact);

        session.setAttribute("message", 
        Message.builder()
        .content("You have successfully added a new contact ")
        .type(MessageType.green)
        .build()
        );

        return"redirect:/user/contacts/add";
    }

    //view contacts

    @RequestMapping
    public String viewContact(
        @ModelAttribute ContactSearchForm contactSearchForm,
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
        
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        


        return "user/contacts";

    }


    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value="page",defaultValue = "0") int page,
        @RequestParam(value="size",defaultValue = AppConstants.PAGE_SIZE +"") int size,
        @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction,
        Model model,
        Authentication authentication
    )
    {
        logger.info(contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userServices.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;

        if(contactSearchForm.getField().equalsIgnoreCase("name"))
        {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        } else if(contactSearchForm.getField().equalsIgnoreCase("email"))
        {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        } else if(contactSearchForm.getField().equalsIgnoreCase("phone"))
        {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        } 

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);


       

        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId, HttpSession session)
    {

        contactService.delete(contactId);

        session.setAttribute("message", 
        Message.builder().content("Contact is Deleted Successfully")
        .type(MessageType.green)
        .build()
        );


        return "redirect:/user/contacts";
    }


    @GetMapping("/view/{contactId}")
    public String uppdateContactFormView(
        @PathVariable("contactId") String contactId,
        Model model)
    {
        var contact = contactService.getById(contactId);

        ContactForm contactForm = new ContactForm();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setLinkedInlink(contact.getLinkedInLink());
        contactForm.setWebsite(contact.getWebsite());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);

        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value="/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId, 
    @Valid @ModelAttribute ContactForm contactForm,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes,
    Model model)
    {

        if(bindingResult.hasErrors())
        {
            return "user/update_contact_view";
        }

        var contact = contactService.getById(contactId);

        contact.setContactId(contactId);

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setWebsite(contactForm.getWebsite());
        contact.setLinkedInLink(contactForm.getLinkedInlink());
        contact.setFavourite(contactForm.isFavourite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        

        //process image

        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty())
        {
            String fileName = UUID.randomUUID().toString();
            String imgeUrl = imageService.uploadImage(contactForm.getContactImage(),fileName);
            contact.setCloudinaryPicPublicId(fileName);
            contact.setPicture(imgeUrl);
            contactForm.setPicture(imgeUrl);
        }

        contactService.update(contact);

         redirectAttributes.addFlashAttribute("message", Message.builder()
                            .content("Contact Updated Successfully!")
                            .type(MessageType.green)
                            .build());


        return "redirect:/user/contacts/view/" + contactId;

    }
}


