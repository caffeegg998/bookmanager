package com.megane.usermanager.controller;

import com.megane.usermanager.Jwt.JwtTokenService;
import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.TokenResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseDTO<TokenResponseDTO> login(
            HttpSession session,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        List<String> authorities = authentication.getAuthorities().stream()
                .map(e -> e.getAuthority()).collect(Collectors.toList());

        String accessToken = jwtTokenService.createToken(username, authorities);
        String refreshToken = jwtTokenService.createRefreshToken(username, authorities);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();

        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);

        return ResponseDTO.<TokenResponseDTO>builder()
                .status(200)
                .data(tokenResponseDTO)
                .build();
    }
    @PostMapping("/access-token")
    public ResponseDTO<?> refreshAccessToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        // Trích xuất refreshToken từ header Authorization
        String refreshToken = extractRefreshTokenFromHeader(refreshTokenHeader);

        // Tiếp tục xử lý như trước
        String newAccessToken = jwtTokenService.refreshAccessToken(refreshToken);

        if (newAccessToken != null) {
            return ResponseDTO.<String>builder()
                    .status(200)
                    .data(newAccessToken)
                    .build();
        } else {
            // Trả về thông báo lỗi nếu không cấp được access token mới từ refresh token
            return ResponseDTO.<String>builder()
                    .status(401)
                    .msg("Invalid refresh token.")
                    .build();
        }
    }

    private String extractRefreshTokenFromHeader(String refreshTokenHeader) {
        // Loại bỏ phần tử "Bearer " trong header để chỉ lấy refreshToken
        return refreshTokenHeader.replace("Bearer ", "");
    }
}
