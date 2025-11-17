package com.Smart_Contact_Manager.demo.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.Smart_Contact_Manager.demo.helper.Message;
import com.Smart_Contact_Manager.demo.helper.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    

                if(exception instanceof DisabledException)
                { 
                HttpSession session = request.getSession();
                session.setAttribute("message", Message.builder().content("user is disabled verification link sent to email").type(MessageType.red).build());


                    response.sendRedirect("/login");
                }

                else{
                    response.sendRedirect("/login?error=true");
                }
    }

}
