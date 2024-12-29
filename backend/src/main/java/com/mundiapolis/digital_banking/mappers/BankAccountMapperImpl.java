package com.mundiapolis.digital_banking.mappers;

import com.mundiapolis.digital_banking.dtos.*;
import com.mundiapolis.digital_banking.entities.*;
import com.mundiapolis.digital_banking.security.entities.AppUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    public AuditLogDTO fromAuditLog(AuditLog auditLog) {
        AuditLogDTO auditLogDTO = new AuditLogDTO();
        BeanUtils.copyProperties(auditLog, auditLogDTO);
        return auditLogDTO;
    }

    public AuditLog fromAuditLogDTO(AuditLogDTO auditLogDTO) {
        AuditLog auditLog = new AuditLog();
        BeanUtils.copyProperties(auditLogDTO, auditLog);
        return auditLog;
    }


    public AppUserDto fromAppUser(AppUser appUser) {
        AppUserDto appUserDTO = new AppUserDto();
        BeanUtils.copyProperties(appUser, appUserDTO);
        return appUserDTO;
    }

    public AppUser fromAppUserDTO(AppUserDto appUserDTO) {
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(appUserDTO, appUser);
        return appUser;
    }
//    public AgentDTO fromAgent(Agent agent){
//        AgentDTO agentDTO=new AgentDTO();
//        agentDTO.setData(fromCustomer(agent.getData()));
//        agentDTO.setAgentId(agent.getAgentId());
//        System.out.println("agentsdroo"+agentDTO);
//        return  agentDTO;
//    }
//    public Agent fromAgentDTO(AgentDTO agentDTO){
//        Agent agent=new Agent();
//        agent.setData(fromCustomerDTO(agentDTO.getData()));
//
//        return  agent;
//    }

    public RequestsDTO fromRequest(Requests requests){
        RequestsDTO request=new RequestsDTO();
        BeanUtils.copyProperties(request,request);
        return  request;
    }
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDTO;
    }
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){
        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;
    }
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }

}
