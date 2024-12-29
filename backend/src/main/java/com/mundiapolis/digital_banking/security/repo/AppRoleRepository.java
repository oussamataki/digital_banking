package com.mundiapolis.digital_banking.security.repo;


import com.mundiapolis.digital_banking.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
}
