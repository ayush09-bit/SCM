package com.Smart_Contact_Manager.demo.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    // Will be injected from application.properties
    private static String BASE_URL;

    @Value("${app.base-url}")
    public void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken token) {

            String clientId = token.getAuthorizedClientRegistrationId();
            OAuth2User oauth2user = (OAuth2User) authentication.getPrincipal();

            // logged in with google
            if ("google".equalsIgnoreCase(clientId)) {
                return oauth2user.getAttribute("email");
            }

            // logged in with github
            if ("github".equalsIgnoreCase(clientId)) {
                return oauth2user.getAttribute("email") != null
                        ? oauth2user.getAttribute("email")
                        : oauth2user.getAttribute("login") + "@gmail.com";
            }
        }

        // logged in with email + password
        return authentication.getName();
    }

    public static String getLinkForEmailVerification(String emailToken) {
        return BASE_URL + "/auth/verify-email?token=" + emailToken;
    }
}
