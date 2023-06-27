package com.megane.usermanager.repo;


import com.megane.usermanager.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    @Query("SELECT cu FROM Customer cu WHERE cu.customerCode LIKE :cu")
    Page<Customer> searchCustomer(@Param("cu") String customerCode, Pageable pageable);
}

