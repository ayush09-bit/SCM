package com.Smart_Contact_Manager.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.Smart_Contact_Manager.demo.users.Contact;
import com.Smart_Contact_Manager.demo.users.User;


public interface ContactService {

    Contact save(Contact contact);
    Contact update(Contact contact);
    List<Contact> getAll();
    Contact getById(String id);
    void delete(String id);
    Page<Contact> searchByName(String nameKeyword,int size,int page,String sortBy,String order,User user);
    Page<Contact> searchByEmail(String emailKeyword,int size,int page,String sortBy,String order,User user);
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword,int size,int page,String sortBy,String order,User user);
    List<Contact> getByUserId(String userId); 
    Page<Contact> getByUser(User user,int page,int size,String sortField, String sortDirection);


}
