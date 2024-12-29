package com.mundiapolis.digital_banking.dtos;

import com.mundiapolis.digital_banking.entities.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data

public class AgentDTO {



    private Long agentId;


    private CustomerDTO  data;


}
