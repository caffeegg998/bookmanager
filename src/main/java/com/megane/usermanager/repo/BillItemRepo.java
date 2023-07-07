package com.megane.usermanager.repo;


import com.megane.usermanager.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepo extends JpaRepository<BillItem, Integer> {

}
