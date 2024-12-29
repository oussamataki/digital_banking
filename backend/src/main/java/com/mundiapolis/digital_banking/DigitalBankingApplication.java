package com.mundiapolis.digital_banking;

import com.mundiapolis.digital_banking.dtos.*;
import com.mundiapolis.digital_banking.exeptions.CustomerNotFoundException;
import com.mundiapolis.digital_banking.security.entities.AppUser;
import com.mundiapolis.digital_banking.security.service.AccountService;
import com.mundiapolis.digital_banking.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService,AccountService accountService){
        return args -> {

            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");



            Stream.of("abdelilah","omar","nisrine").forEach(name -> {
                // Check if a customer with the same name already exists

                        CustomerDTO customer = new CustomerDTO();


                    customer.setName(name);

                    customer.setEmail(name + "@gmail.com");
                    customer.setAdresse("Casablanca");
                    customer.setPhoneNumber("07100682");



                try {
                    accountService.addNewUser(name, "1234", customer.getEmail(), "1234");

                    AppUser user = accountService.loadUserByUsername(name);


                    customer.setAppUser(user);


                    if(!name.equals("omar")) {
                        accountService.addRoleToUser(name, "ADMIN");
                    }
                    accountService.addRoleToUser(name, "USER");
                    System.out.println("6");

                    bankAccountService.saveCustomer(customer);
                    System.out.println("7");

                } catch (Exception e) {
                    System.err.println("Error creating customer for name " + name + ": " + e.getMessage());
                }


            });

            bankAccountService.listCustomers().forEach(customer->{

                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i <10 ; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    } else{
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
