package com.mundiapolis.digital_banking.web;

import com.mundiapolis.digital_banking.dtos.*;
import com.mundiapolis.digital_banking.enums.RequestStatus;
import com.mundiapolis.digital_banking.exeptions.BalanceNotSufficientException;
import com.mundiapolis.digital_banking.exeptions.BankAccountNotFoundException;
import com.mundiapolis.digital_banking.exeptions.CustomerNotFoundException;
import com.mundiapolis.digital_banking.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }

    @GetMapping("/accounts/{accountId}/op")
    public List<AccountOperationDTO> accountHistory(
            @PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.accountHistory(accountId);
    }




    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        System.out.println("heeeere"+debitDTO);
        this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }

    @GetMapping("/request")
    public List<RequestsDTO> requestsDTOS() {
        return bankAccountService.listRequests();
    }

    @PostMapping("/request/")
    public void request(@RequestBody RequestsDTO request) {

        this.bankAccountService.saveRequest(request.getType(), request.getCustomer(),request.getDescription());

    }


    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount()
                
                
                );

    }
    @PostMapping("/customers/{customerId}/current-accounts")
    public CurrentBankAccountDTO saveCurrentBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double overDraft,
            @PathVariable Long customerId) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(initialBalance, overDraft, customerId);
    }

    @PostMapping("/customers/{customerId}/saving-accounts")
    public SavingBankAccountDTO saveSavingBankAccount(
            @RequestParam double initialBalance,
            @RequestParam double interestRate,
            @PathVariable Long customerId) throws CustomerNotFoundException {
        return bankAccountService.saveSavingBankAccount(initialBalance, interestRate, customerId);
    }
    @GetMapping("/customers/{customerId}/accounts")
    public List<BankAccountDTO> getBankAccountsByCustomerId(@PathVariable Long customerId) {
        List<BankAccountDTO> bankAccountDTOS = bankAccountService.getBankAccountsByCustomerId(customerId);
        return bankAccountDTOS;
    }

    @PostMapping("/log")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public AuditLogDTO saveAudit(AuditLogDTO auditLogDTO)  {
        return bankAccountService.saveLog(auditLogDTO);
    }
    @GetMapping("/log")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<AuditLogDTO> getAudit() {
        return bankAccountService.listAuditLogDTO();
    }

    @GetMapping("/log/search")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<AuditLogDTO> searchLog(@RequestParam(name="id", defaultValue = "") Long id) {
        return bankAccountService.searchLogByAgentID(id);
    }
}
