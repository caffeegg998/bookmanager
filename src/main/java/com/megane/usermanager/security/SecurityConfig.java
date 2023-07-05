package com.megane.usermanager.security;

import com.megane.usermanager.Jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,
        prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Autowired
    JwtTokenFilter tokenFilter;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void config(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());

        // fixed cứng
        // 	auth.inMemoryAuthentication()
        // 		.withUser("admin")
        // 		.password(new BCryptPasswordEncoder().encode("123456"))
        // 		//.roles("ADMIN") // ROLE_ADMIN
        // 		.authorities("ADMIN")
        // 		.and()
        // 		.passwordEncoder(new BCryptPasswordEncoder());
        // }
    }
    //md5, bcrypt

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/login","/api/usermanager/**")
                // .hasAnyRole("ADMIN")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_SUBADMIN")
                .requestMatchers("/api/usermanager/**","/api/transactionmanager/**","/api/bookmanager/**")
                .authenticated()
                .requestMatchers("/staff/**")
                // .hasAnyRole("ADMIN")
                .hasAnyAuthority("ROLE_STAFF")
                .requestMatchers("/bookmanager/**")
                .authenticated()
                .requestMatchers("/customer/**")
                // .hasAnyRole("ADMIN")
                .hasAnyAuthority("ROLE_CUSTOMER")
                .requestMatchers("/books/**")
                .authenticated()
                .requestMatchers("/guest/**")
                // .hasAnyRole("ADMIN")
                .hasAnyAuthority("ROLE_GUEST")
                .requestMatchers("/books/list","/books/category","/book/")
                .authenticated()
//                .anyRequest().permitAll()
                .and()
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .csrf((csrf) -> csrf.disable())
                .httpBasic(withDefaults());
        //apply filter
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}