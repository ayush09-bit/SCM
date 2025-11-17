package com.Smart_Contact_Manager.demo.services;

import org.springframework.web.multipart.MultipartFile;

public interface imageService {
    

    String uploadImage(MultipartFile contactImage , String filename);
    String getUrlFromPublicId(String publicid);



}
