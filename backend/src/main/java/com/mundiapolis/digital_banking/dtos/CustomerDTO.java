package com.mundiapolis.digital_banking.dtos;

import com.mundiapolis.digital_banking.entities.Document;
import com.mundiapolis.digital_banking.security.entities.AppUser;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerDTO {
    private Long id;
    private String name;

    private String surName;
    private String email;

    private String image;

    private String phoneNumber;

    private String adresse;

    private LocalDateTime timestamp;
    @NotNull
    private AppUser appUser;

    private List<Document> documents;

}
