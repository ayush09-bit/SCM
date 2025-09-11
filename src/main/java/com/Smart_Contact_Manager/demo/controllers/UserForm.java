package com.Smart_Contact_Manager.demo.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserForm {
    private String name;
    private String email;
    private String password;
    private String about;
    private String phonenumber;


}
