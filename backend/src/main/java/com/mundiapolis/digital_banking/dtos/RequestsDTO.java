package com.mundiapolis.digital_banking.dtos;

import com.mundiapolis.digital_banking.entities.Customer;
import com.mundiapolis.digital_banking.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data

public class RequestsDTO {
    private Long id;
    private LocalDateTime timestamp;
    private RequestStatus type;
    private CustomerDTO customer;
    private String description;

}
