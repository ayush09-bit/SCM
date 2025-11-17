package com.Smart_Contact_Manager.demo.services.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.Smart_Contact_Manager.demo.services.imageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageServiceImpl implements imageService {

    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public  String uploadImage(MultipartFile contactImage, String filename) {

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id",filename));

            return this.getUrlFromPublicId(filename);

        } catch (Exception e) {

            return null;
            
        }
        
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
                .width(500)
                .height(500) //2.57.30
                .crop("fill"))
                .generate(publicId);
        
    }

}
