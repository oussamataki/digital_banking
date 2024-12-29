package com.mundiapolis.digital_banking.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLogDTO {
    private Long id;
    private Long idUser;
    private Long idClient;
    private LocalDateTime timestamp;
    private String operation;
    private String type;
    private String details;
}
