package com.example.mhb.repository;

import com.example.mhb.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByNida(String nida);

    List<Customer> findByContact(String contact); // ADDED FOR DUPLICATE CHECK
}
