package com.megane.usermanager;

//import com.megane.usermanager.security.SecurityInterceptor;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@OpenAPI30
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
	@Bean
	NewTopic notification(){
		return new NewTopic("notification",2,(short) 1);
	}
//	@Bean
//	NewTopic notificationPassword(){
//		return new NewTopic("notificationPassword",2,(short) 1);
//	}

	@Bean
	NewTopic status(){
		return new NewTopic("statistic",1,(short) 1);
	}
}
