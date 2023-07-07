package com.megane.usermanager.repo;

import com.megane.usermanager.entity.Bill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BillDAO {
	@PersistenceContext
	EntityManager entityManager;

	// nho dung ham nay o 1 class moi trong lop repo
	@SuppressWarnings("unchecked")
	public List<Bill> searchByDate(Date s) {
		String jpql = "SELECT b FROM Bill b WHERE b.createdAt >= :x ";
		
		if (s == null)
			jpql = "SELECT b FROM Bill b";
		
		//criteria , 
		return entityManager.createQuery(jpql)
				.setParameter("x", s)
				.setMaxResults(10)
				.setFirstResult(0)
				.getResultList();
	}
}
