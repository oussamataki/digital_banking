package com.mundiapolis.digital_banking.services;


import com.mundiapolis.digital_banking.dtos.*;
import com.mundiapolis.digital_banking.entities.AccountOperation;
import com.mundiapolis.digital_banking.entities.AuditLog;
import com.mundiapolis.digital_banking.entities.Customer;
import com.mundiapolis.digital_banking.enums.RequestStatus;
import com.mundiapolis.digital_banking.exeptions.BalanceNotSufficientException;
import com.mundiapolis.digital_banking.exeptions.BankAccountNotFoundException;
import com.mundiapolis.digital_banking.exeptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
//    AgentDTO saveAgent(AgentDTO agentDTO);
//
//    List<AgentDTO> listAgentDTO();
//    List<Agent> listAgent();

    AppUserDto saveUser(AppUserDto appUserDto);

    List<AuditLogDTO> listAuditLogDTO();

    AuditLogDTO saveLog(AuditLogDTO logDTO);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws
            CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws
            CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException,
            BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws
            BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);

//    AgentDTO getAgent(Long agentId) throws CustomerNotFoundException, CustomerNotFoundException;
//
//    AgentDTO updateAgent(AgentDTO agentDTO);
//
//    void deleteAgent(Long agentId);

    List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
    List<BankAccountDTO> getBankAccountsByCustomerId(Long customerId);

    List<RequestsDTO> listRequests();

//    List<CustomerDTO> searchAgents(String s);

    List<AuditLogDTO> searchLogByAgentID(Long id);

    void saveRequest(RequestStatus type, CustomerDTO customer, String description);
}
