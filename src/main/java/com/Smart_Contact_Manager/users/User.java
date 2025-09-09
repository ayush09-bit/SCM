package com.Smart_Contact_Manager.users;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id

    private String userId;
    @Column(name="user_name", nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    @Column(columnDefinition = "TEXT")
    private String about;
    @Column(columnDefinition = "TEXT")
    private String profilepic;
    private String phonenumber;


    private boolean enabled=false;
    private boolean emailverified=false;
    private boolean phoneverified=false;

    // self, google, github
    @Enumerated(EnumType.STRING)
    private Providers provider=Providers.SELF;
    private String provideruserId;


    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contact = new ArrayList<>();

}
