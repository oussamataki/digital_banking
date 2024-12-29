package com.mundiapolis.digital_banking.repositories;

import com.mundiapolis.digital_banking.entities.Requests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<Requests, String> {
}
