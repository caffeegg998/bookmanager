package com.megane.usermanager;

import com.megane.usermanager.entity.Role;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.repo.RoleRepo;
import com.megane.usermanager.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
@Slf4j
public class DataInjection implements ApplicationRunner {

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	UserRepo userRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// insert data demo into data
		log.info("BEGIN INSERT ROLE DUMP");
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		if (roleRepo.findByName(role.getName()) == null ) {
			try {
				log.info("INSERT DUMP" + role.getId());
				roleRepo.save(role);
				User user = new User();
				user.setUsername("sysadmin");
				user.setPassword(new BCryptPasswordEncoder().encode("123456"));
				user.setFullName("SYS ADMIN");
				user.setEmail("caffeegg998@gmail.com");
				user.setPhoneNumber("0348533336");
				user.setBirthDate(new Date());
				user.setRoles(Arrays.asList(role));

				
				userRepo.save(user);
			} catch (Exception e) {
			}
		}
	}
}
