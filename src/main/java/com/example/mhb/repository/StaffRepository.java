
package com.example.mhb.repository;
import com.example.mhb.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
  Optional<Staff> findByUsername(String username);
}

// SSJ 
