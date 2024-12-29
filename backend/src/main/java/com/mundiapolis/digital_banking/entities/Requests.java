package com.mundiapolis.digital_banking.entities;

import com.mundiapolis.digital_banking.enums.OperationType;
import com.mundiapolis.digital_banking.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private RequestStatus type;
    @OneToOne
    private Customer customer;


    private String description;

}
