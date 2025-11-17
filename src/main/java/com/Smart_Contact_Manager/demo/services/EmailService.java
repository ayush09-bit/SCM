package com.Smart_Contact_Manager.demo.services;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithHtml();

    void sendEmailWithAttachment();

}
