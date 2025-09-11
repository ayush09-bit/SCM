package com.Smart_Contact_Manager.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig { 

    // user create and login using java code with in memeory service
    
//    @Bean
//     public UserDetailsService userDetailsService()
//     {
//         UserDetails user1 = User.withUsername("admin1")
//         .withDefaultPasswordEncoder()
//         .password("admin1")
//         .roles("ADMIN")
//         .build();

//         var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
//         return inMemoryUserDetailsManager;
//     }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service object
        daoAuthenticationProvider.setUserDetailsService(null);
        //user password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
