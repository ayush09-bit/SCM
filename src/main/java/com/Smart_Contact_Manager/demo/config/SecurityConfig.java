package com.Smart_Contact_Manager.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Smart_Contact_Manager.demo.services.Impl.SecurityCustomerUserDetailService;


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



    //configuration of authentication provider for security
    @Autowired
    private SecurityCustomerUserDetailService  userDetailsService;

    @Autowired
    private OAuthAuthencationSuccessHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    // @Bean
    // public DaoAuthenticationProvider  authenticationProvider()
    // {
    //     DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    //     // user detail service object
    //     daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    //     //user password encoder object
    //     daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

    //     return daoAuthenticationProvider;

    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        //configuration

        httpSecurity.authorizeHttpRequests(authorize->{
            //authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
            //form default login
           // httpSecurity.formLogin(Customizer.withDefaults());

           httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            // formLogin.failureForwardUrl("/login?error=true");
            // formLogin.defaultSuccessUrl("/home");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            formLogin.failureHandler(authFailureHandler);

            
           });


           httpSecurity.oauth2Login(oauth2 -> 
             {oauth2
             .loginPage("/login")
             .successHandler(handler);
             }
        
             );

        
           httpSecurity.csrf(AbstractHttpConfigurer::disable);
           httpSecurity.logout(logoutForm ->
           logoutForm
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID", "OAUTH2_AUTHORIZATION_REQUEST")
           );

           

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    
}
