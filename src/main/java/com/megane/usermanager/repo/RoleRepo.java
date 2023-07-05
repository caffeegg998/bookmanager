package com.megane.usermanager.repo;

import com.megane.usermanager.entity.Customer;
import com.megane.usermanager.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {

    @Query("SELECT ro FROM Role ro WHERE ro.name LIKE :ro")
    Page<Role> searchRole(@Param("ro") String name, Pageable pageable);

}
