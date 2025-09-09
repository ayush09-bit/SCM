package com.Smart_Contact_Manager.users;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contact {
    @Id
    private String contactId;
    private String name;
    private String email;
    private String Pnumber;
    private String address;
    private String picture;
    @Column(length = 10000)
    private String description;
    private boolean favourite = false;
    private String linkedInlinks; 

    @ManyToOne
    private User user; 

     @OneToMany(mappedBy = "contact" , cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Social_links> links = new ArrayList<>();

}
