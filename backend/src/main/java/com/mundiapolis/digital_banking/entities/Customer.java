package com.mundiapolis.digital_banking.entities;

import com.mundiapolis.digital_banking.security.entities.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surName;

    private String phoneNumber;

    private String adresse;

    private String image;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "customer")
    private List<BankAccount> bankAccounts;

    @OneToOne()
    private AppUser appUser;

    @CreationTimestamp
    private LocalDateTime timestamp;



    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<Document> documents;


}
