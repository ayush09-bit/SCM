package com.Smart_Contact_Manager.demo.services.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Smart_Contact_Manager.demo.helper.ResourceNotFoundException;
import com.Smart_Contact_Manager.demo.repositories.ContactRepo;
import com.Smart_Contact_Manager.demo.services.ContactService;
import com.Smart_Contact_Manager.demo.users.Contact;
import com.Smart_Contact_Manager.demo.users.User;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setContactId(contactId);
        return contactRepo.save(contact);

    }

    @Override
    public Contact update(Contact contact) {

        var oldContact = contactRepo.findById(contact.getContactId()).orElseThrow(() -> new ResourceNotFoundException("contact not found"));


        oldContact.setName(contact.getName()); 
        oldContact.setEmail(contact.getEmail()); 
        oldContact.setAddress(contact.getAddress()); 
        oldContact.setDescription(contact.getDescription()); 
        oldContact.setLinkedInLink(contact.getLinkedInLink()); 
        oldContact.setFavourite(contact.isFavourite()); 
        oldContact.setPhoneNumber(contact.getPhoneNumber()); 
        oldContact.setPicture(contact.getPicture()); 
        oldContact.setWebsite(contact.getWebsite());
        oldContact.setCloudinaryPicPublicId(contact.getCloudinaryPicPublicId());  
        

        return contactRepo.save(oldContact);
           
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("not contact for this is"+id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("not contact for this is"+id));
        contactRepo.delete(contact);    
    }

  

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page,int size, String sortBy , String direction) {


        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size,sort);

       return contactRepo.findByUser(user,pageable);  
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order,User user) {
       
         Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
         var pageable = PageRequest.of(page,size,sort);

         return contactRepo.findByUserAndNameContaining(user,nameKeyword,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,User user) {
         Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
         var pageable = PageRequest.of(page,size,sort);

         return contactRepo.findByUserAndEmailContaining(user,emailKeyword,pageable);
   }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,String order,User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
         var pageable = PageRequest.of(page,size,sort);

         return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword,pageable);
   }

    

}
