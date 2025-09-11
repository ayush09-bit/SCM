package com.Smart_Contact_Manager.demo.helper;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg)
    {
        super(msg);
    }
    public ResourceNotFoundException()
    {
        super("Resource not found");
    }

}
