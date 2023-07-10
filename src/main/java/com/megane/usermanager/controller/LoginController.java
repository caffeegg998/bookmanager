package com.megane.usermanager.controller;

import com.megane.usermanager.Jwt.JwtTokenService;
import com.megane.usermanager.dto.ResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller //MVC
@RestController //Rest API
@RequestMapping
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;
    //java annotation
    // @GetMapping("/login")
    // public String login() {
    // 	// map url vao 1 ham, tra ve ten file view
    // 	return "login.html";
    // }

    @PostMapping("/login")
    public ResponseDTO<String> login(
            HttpSession session,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        List<String> authorities = authentication.getAuthorities().stream()
                .map(e -> e.getAuthority()).collect(Collectors.toList());

        return ResponseDTO.<String>builder()
                .status(200)
                .data(jwtTokenService.createToken(username, authorities))
                .build();
    }
}
