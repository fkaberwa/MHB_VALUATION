package com.example.mhb.repository;

import com.example.mhb.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByNida(String nida);

    boolean existsByContact(String contact);
}