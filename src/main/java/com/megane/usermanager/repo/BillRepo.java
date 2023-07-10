package com.megane.usermanager.repo;

import com.megane.usermanager.dto.BillStatisticDTO;
import com.megane.usermanager.entity.Bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Integer> {

	@Query("SELECT bi FROM Bill bi WHERE bi.createdAt >= :bi")
	List<Bill> searchByDate(@Param("bi") Date s);

	/// Đếm số lượng đơn group by MONTH(buyDate)
	// - dùng custom object để build
	// SELECT id, MONTH(buyDate) from bill;
	// select count(*), MONTH(buyDate) from bill
	// group by MONTH(buyDate)
	@Query("SELECT count(bi.id), month(bi.createdAt), year(bi.createdAt) "
			+ "FROM Bill bi GROUP BY month(bi.createdAt), year(bi.createdAt) ")
	List<Object[]> thongKeBill();

//	@Query("SELECT new com.megane.usermanager.dto.BillDTO(count(bi.id), '/') "
//			+ " FROM Bill bi GROUP BY month(bi.createdAt), year(bi.createdAt) ")
//	List<BillStatisticDTO> thongKeBill2();
}
