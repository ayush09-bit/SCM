package com.Smart_Contact_Manager.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.Smart_Contact_Manager.demo.helper.Helper;
import com.Smart_Contact_Manager.demo.helper.Message;
import com.Smart_Contact_Manager.demo.helper.MessageType;
import com.Smart_Contact_Manager.demo.repositories.UserRepopsitory;
import com.Smart_Contact_Manager.demo.services.EmailService;
import com.Smart_Contact_Manager.demo.users.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepopsitory userRepo;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    
                

                if(exception instanceof DisabledException)
                { 
                
                String email = request.getParameter("email");
            User user = userRepo.findByEmail(email).orElse(null);

            if (user != null) {
                String token = user.getEmailToken();

               String verifyLink = Helper.getLinkForEmailVerification(token);
               // String verifyLink = "http://localhost:8080/auth/verify-email?token=" + token;


                emailService.sendEmail(
                    email,
                    "Verify your Smart Contact Manager account",
                    "Click the link to verify your account:\n" + verifyLink
                );
            }

            HttpSession session = request.getSession();
            session.setAttribute("message",
                    Message.builder()
                    .content("User is disabled. Verification link sent to email")
                    .type(MessageType.red).build());

            response.sendRedirect("/login");
                }

                else{
                    response.sendRedirect("/login?error=true");
                }
    }

}
