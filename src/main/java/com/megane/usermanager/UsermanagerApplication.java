package com.megane.usermanager;

//import com.megane.usermanager.security.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class UsermanagerApplication {

//	@Autowired
//	SecurityInterceptor securityInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(UsermanagerApplication.class, args);
	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(securityInterceptor);
//	}
}
