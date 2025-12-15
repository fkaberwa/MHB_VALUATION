package com.example.mhb.repository;

import com.example.mhb.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByNida(String nida);

    Optional<Customer> findByContact(String contact);

    boolean existsByNida(String nida);

    boolean existsByContact(String contact);
}
