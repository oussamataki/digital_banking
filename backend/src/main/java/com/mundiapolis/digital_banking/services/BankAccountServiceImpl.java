package com.mundiapolis.digital_banking.services;

import com.mundiapolis.digital_banking.dtos.*;
import com.mundiapolis.digital_banking.entities.*;
import com.mundiapolis.digital_banking.enums.OperationType;
import com.mundiapolis.digital_banking.enums.RequestStatus;
import com.mundiapolis.digital_banking.exeptions.BalanceNotSufficientException;
import com.mundiapolis.digital_banking.exeptions.BankAccountNotFoundException;
import com.mundiapolis.digital_banking.exeptions.CustomerNotFoundException;
import com.mundiapolis.digital_banking.mappers.BankAccountMapperImpl;
import com.mundiapolis.digital_banking.repositories.*;
import com.mundiapolis.digital_banking.security.entities.AppUser;
import com.mundiapolis.digital_banking.security.repo.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private final AppUserRepository appUserRepository;
    private final AuditLogRepository auditLogRepository;
//    private AgentAccount agentAccount;
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
    private RequestsRepository requestsRepository;


    @Override
    public AppUserDto saveUser(AppUserDto appUserDto) {
        log.info("Saving new Agent");
        AppUser age=dtoMapper.fromAppUserDTO(appUserDto);
        System.out.println("Mapper"+age);
        AppUser savedAgent = appUserRepository.save(age);
        return dtoMapper.fromAppUser(savedAgent);

    }
//
//    @Override
//    public List<AgentDTO> listAgentDTO() {
//        List<Agent> agents = agentAccount.findAll();
//        List<AgentDTO> agentDTO = agents.stream().map(ag->dtoMapper.fromAgent(ag)).collect(Collectors.toList());
//        return agentDTO;
//    }

//    @Override
//    public List<Agent> listAgent() {
//        List<Agent> agents = agentAccount.findAll();
//        return agents;
//    }

    @Override
    public List<AuditLogDTO> listAuditLogDTO() {
        List<AuditLog> auditLogs = auditLogRepository.findAll(); // Assuming auditLogRepository is your repository
        List<AuditLogDTO> auditLogDTOs = auditLogs.stream()
                .map(auditLog -> dtoMapper.fromAuditLog(auditLog))
                .collect(Collectors.toList());
        return auditLogDTOs;
    }



    @Override
    public AuditLogDTO saveLog(AuditLogDTO logDTO) {
        log.info("Saving new Log");
        AuditLog log=dtoMapper.fromAuditLogDTO(logDTO);
        AuditLog savedLog = auditLogRepository.save(log);
        return dtoMapper.fromAuditLog(savedLog);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date()); currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft); currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }
    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
            throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date()); savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }
    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
/*
List<CustomerDTO> customerDTOS=new ArrayList<>();
for (Customer customer:customers){
CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
customerDTOS.add(customerDTO);
} */
        return customerDTOS;
    }
    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }
    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException,
            BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount); accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date()); accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation); bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws
            BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }
    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    //agent

//    @Override
//    public AgentDTO getAgent(Long agentId) throws CustomerNotFoundException {
//        Agent agent = agentAccount.findById(agentId)
//                .orElseThrow(() -> new CustomerNotFoundException("Agent Not found"));
//        return dtoMapper.fromAgent(agent);
//    }
//    @Override
//    public AgentDTO updateAgent(AgentDTO agentDTO) {
//        log.info("Saving new Agent");
//        Agent agent=dtoMapper.fromAgentDTO(agentDTO);
//        Agent savedAgent = agentAccount.save(agent);
//        return dtoMapper.fromAgent(savedAgent);
//    }
//    @Override
//    public void deleteAgent(Long agentId){
//        agentAccount.deleteById(agentId);
//    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }
    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op ->
                dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
        List<CustomerDTO> customersDTOS= customers.stream().map(cust->dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customersDTOS;
    }

    @Override
    public List<RequestsDTO> listRequests() {
        List<Requests> requests=requestsRepository.findAll();
        List<RequestsDTO> requestDTOS= requests.stream().map(cust->dtoMapper.fromRequest(cust)).collect(Collectors.toList());
        return requestDTOS;
    }


//    @Override
//    public List<CustomerDTO> searchAgents(String keyword) {
//        List<Customer> customers=agentAccount.searchAgent(keyword);
//        List<CustomerDTO> customersDTOS= customers.stream().map(cust->dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
//        return customersDTOS;
//    }

    @Override
    public List<AuditLogDTO> searchLogByAgentID(Long id) {
        List<AuditLog> log=auditLogRepository.searchLogByID(id);
        List<AuditLogDTO> logDTO= log.stream().map(l->dtoMapper.fromAuditLog(l)).collect(Collectors.toList());
        return logDTO;
    }


    @Override
    public void saveRequest(RequestStatus type, CustomerDTO customer, String description) {
        Requests request = new Requests();
        request.setCustomer(dtoMapper.fromCustomerDTO(customer));
        request.setType(type);
        request.setDescription(description);
        requestsRepository.save(request);
    }

    @Override
    public List<BankAccountDTO> getBankAccountsByCustomerId(Long customerId) {
        List<BankAccount> bankAccountList = bankAccountRepository.getBankAccountByCustomer_Id(customerId);
        List<BankAccountDTO> bankAccountDTOS;

        bankAccountDTOS = bankAccountList.stream().map(bankAccount -> {
            if(bankAccount instanceof CurrentAccount) {
                return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
            } else {
                return dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountDTOS;
    }
}
