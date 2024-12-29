package com.mundiapolis.digital_banking.security.service;

import com.mundiapolis.digital_banking.security.entities.AppRole;
import com.mundiapolis.digital_banking.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username,String role);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByUsername(String username);

}
