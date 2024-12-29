package com.mundiapolis.digital_banking.repositories;

import com.mundiapolis.digital_banking.entities.AuditLog;
import com.mundiapolis.digital_banking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
    @Query("select c from AuditLog c where c.idClient = :id")
    List<AuditLog> searchLogByID(@Param("id") Long id);
}
