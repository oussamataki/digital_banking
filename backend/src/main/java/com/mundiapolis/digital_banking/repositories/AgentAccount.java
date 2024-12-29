//package com.mundiapolis.digital_banking.repositories;
//
//import com.mundiapolis.digital_banking.entities.Agent;
//import com.mundiapolis.digital_banking.entities.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface AgentAccount extends JpaRepository<Agent,Long> {
//    @Query("select c from Customer c where c.id=(select d.data.id from Agent d where d.data.name like :kw)")
//    List<Customer> searchAgent(@Param("kw") String keyword);
//}
