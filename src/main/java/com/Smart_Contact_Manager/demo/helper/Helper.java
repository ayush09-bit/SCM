package com.Smart_Contact_Manager.demo.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication)
    {
       

        //how to find email b
        if(authentication instanceof OAuth2AuthenticationToken)
        {
           var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2user = (OAuth2User)authentication.getPrincipal();
            var username = "";


            //logged in with google
            if(clientId.equalsIgnoreCase("google"))
            {
                System.out.println("google client");
                username = oauth2user.getAttribute("email").toString();
                
            }
        //logged in with github
          else if(clientId.equalsIgnoreCase("github"))
          {
            System.out.println("loggeg in from github");
            username = oauth2user.getAttribute("email") != null ?oauth2user.getAttribute("email").toString() : oauth2user.getAttribute("login").toString() + "@gmail.com";
            
          }
          return username;

        }
        //logged in with emaail and password
        else{
            System.out.println("logged in from database");
            return authentication.getName();
        }

        
    }

}
