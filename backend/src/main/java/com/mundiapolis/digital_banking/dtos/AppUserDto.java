package com.mundiapolis.digital_banking.dtos;

import com.mundiapolis.digital_banking.security.entities.AppRole;

import java.util.List;

public class AppUserDto {

    public String username;
    public String email;

    public String password;

    public List<AppRole> roleNames;
}
