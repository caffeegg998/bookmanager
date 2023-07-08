package com.megane.usermanager.service;

import com.megane.usermanager.entity.Bill;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.repo.BillRepo;
import com.megane.usermanager.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class JobScheduler {
	@Autowired
	UserRepo userRepo;

	@Autowired
	BillRepo billRepo;

	@Autowired
	MailService mailService;

	// Lên lịch quét 5 phút 1 lần, xem có đơn hàng mới ko, thì gửi mặc định về tài
	// khoản email của mình,
	// (Đơn hàng mới là ngày tạo > ngày hiện tại - 5 phút )
	// gợi ý: Viết hàm jpql tìm bill theo buyDate > :date
	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void sendAdminEmail() {
		// chi gio hien tai - 5 phut
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);
		Date date = cal.getTime();

		List<Bill> bills = billRepo.searchByDate(date);

		for (Bill b : bills) {
			System.out.println(b.getId());

			mailService.sendEmail("caffeegg998@gmail.com", "Khách hàng có ID: " + b.getId() + "vừa đặt hàng!", "aa");
		}

	}

	// GUI EMAIL SINH NHAT
//	@Scheduled(fixedDelay = 5000)
	// QUARZT SCHEDULER
	// GIAY - PHUT - GIO - NGAY - THANG - THU
	// https://crontab.guru/

//	@Scheduled(cron = "0 0 9 * * *")
//	public void sendEmail() {
//		System.out.println("HELLO JOB");
//		// chi lay ngay
//		Calendar cal = Calendar.getInstance();
//
//		List<User> users = userRepo.searchByBirthday(cal.get(Calendar.DATE), cal.get(Calendar.MONTH) + 1);
//
//		for (User u : users) {
//			System.out.println(u.getName());
//			mailService.sendEmail(u.getEmail(), "HPBD", "aa");
//		}
//	}
}
