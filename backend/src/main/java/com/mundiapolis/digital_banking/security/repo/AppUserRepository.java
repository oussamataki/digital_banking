package com.mundiapolis.digital_banking.security.repo;


import com.mundiapolis.digital_banking.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
