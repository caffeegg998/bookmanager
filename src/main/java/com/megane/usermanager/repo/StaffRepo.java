package com.megane.usermanager.repo;

import com.megane.usermanager.entity.Customer;
import com.megane.usermanager.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Integer> {
    @Query("SELECT st FROM Staff st WHERE st.staffCode LIKE :st")
    Page<Staff> searchStaff(@Param("st") String staffCode, Pageable pageable);
}
