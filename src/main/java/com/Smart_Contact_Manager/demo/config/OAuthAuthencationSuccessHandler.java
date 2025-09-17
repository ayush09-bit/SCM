package com.Smart_Contact_Manager.demo.config;



import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.Smart_Contact_Manager.demo.helper.AppConstants;
import com.Smart_Contact_Manager.demo.repositories.UserRepopsitory;
import com.Smart_Contact_Manager.demo.users.Providers;
import com.Smart_Contact_Manager.demo.users.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;


@Component
public class OAuthAuthencationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepopsitory userrepo;

    Logger logger = LoggerFactory.getLogger(OAuthAuthencationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        
        logger.info("OAuthAuthencationSuccessHandler");

            //identify the provider

            var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

            String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

            logger.info(authorizedClientRegistrationId);

            var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

            oauthUser.getAttributes().forEach((key,value) ->
            {
                logger.info(key + " : " + value);
            });

            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setRoleList(List.of(AppConstants.ROLE_USER));
            user.setEmailverified(true);
            user.setEnabled(true);

        //google login
            if(authorizedClientRegistrationId.equalsIgnoreCase("google"))
            {
                user.setEmail(oauthUser.getAttribute("email").toString());
                user.setProfilepic(oauthUser.getAttribute("picture").toString());
                user.setName(oauthUser.getAttribute("name").toString());
                user.setProvideruserId(oauthUser.getName());
                user.setProvider(Providers.GOOGLE);
                user.setPassword("****");
                user.setAbout("this is google created account");

            }

        //gitjub login
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github"))
            {
                String email = oauthUser.getAttribute("email") != null ?oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";
                
                user.setEmail(email);
                user.setProfilepic(oauthUser.getAttribute("avatar_url").toString());
                user.setName(oauthUser.getAttribute("login").toString());
                user.setProvideruserId(oauthUser.getName());
                user.setProvider(Providers.GITHUB);
                user.setPassword("****");
                user.setAbout("this is github created account");


            }
        
        //linkedin login
        else if(authorizedClientRegistrationId.equalsIgnoreCase("linkedin"))
        {

        } 
        else{

            logger.info(authorizedClientRegistrationId + " unknown");
        }




        new DefaultRedirectStrategy().sendRedirect(request, response,"/user/profile");

        // DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
           
       // logger.info(user.getName());

        // user.getAttributes().forEach((key, value)->{
        //     logger.info("{} => {}" ,key, value);
        // });

        // String email = user.getAttribute("email").toString();
        // String name = user.getAttribute("name").toString();
        // String picture = user.getAttribute("picture").toString();
        //String password = user.getAttribute("password").toString();

       // logger.info(user.getAuthorities().toString());

    //    User user1 = new User();
    //     user1.setEmail(email);
    //     user1.setName(name);
    //     user1.setProfilepic(picture);
    //     user1.setPassword("password");
    //     user1.setUserId(UUID.randomUUID().toString());
    //     user1.setProvider(Providers.GOOGLE);
    //     user1.setEnabled(true);

    //     user1.setEmailverified(true);
    //     user1.setProvideruserId(user.getName());
    //     user1.setRoleList(List.of("ROLE_USER"));
    //     user1.setAbout(name);
        

        User checkuser = userrepo.findByEmail(user.getEmail()).orElse(null);

        if(checkuser == null)
        {
            userrepo.save(user);
            logger.info("user saved " + user.getEmail());
        }
           

                
    }
    

}
