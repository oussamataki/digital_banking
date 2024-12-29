//package com.mundiapolis.digital_banking.web;
//
//import com.mundiapolis.digital_banking.dtos.AgentDTO;
//import com.mundiapolis.digital_banking.dtos.AgentDTO;
//import com.mundiapolis.digital_banking.dtos.CustomerDTO;
//import com.mundiapolis.digital_banking.exeptions.CustomerNotFoundException;
//import com.mundiapolis.digital_banking.services.BankAccountService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@AllArgsConstructor
//@Slf4j
//@CrossOrigin("*")
//public class AgentRestController {
//    private BankAccountService bankAccountService;
//
//    @PostMapping("/agent")
//    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
//    public AgentDTO saveAgent(@RequestBody AgentDTO agentDTO) {
//        return bankAccountService.saveAgent(agentDTO);
//    }
//    @GetMapping("/agent")
//    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
//    public List<AgentDTO> agent() {
//
//        return bankAccountService.listAgentDTO();
//    }
//
//
//
//    @GetMapping("/agent/search")
////    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
//    public List<CustomerDTO> searchAgents(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
//        return bankAccountService.searchAgents("%" + keyword + "%");
//    }
//
//    @GetMapping("/agent/{id}")
//    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
//    public AgentDTO getAgent(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
//        return bankAccountService.getAgent(customerId);
//    }
//
////    @PostMapping("/agent")
////    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
////    public AgentDTO saveAgent(@RequestBody AgentDTO customerDTO) {
////        return bankAccountService.saveAgent(customerDTO);
////    }
//
////    @PutMapping("/agent/{agentId}")
////    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
////    public AgentDTO updateAgent( @RequestBody AgentDTO agentDTO) {
////        bankAccountService.updateCustomer(agentDTO.getData());
////        return agentDTO;
////    }
//
//    @DeleteMapping("/agent/{id}")
//    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
//    public void deleteAgent(@PathVariable Long id) {
//        bankAccountService.deleteAgent(id);
//    }
//
//
//
//}
