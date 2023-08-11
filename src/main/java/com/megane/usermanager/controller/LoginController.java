package com.megane.usermanager.controller;

import com.megane.usermanager.Jwt.JwtTokenService;
import com.megane.usermanager.dto.CurrentUser;
import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.TokenResponseDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.service.interf.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController //Rest API
@RequestMapping("/api")

public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    UserService userService;
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

        UserDTO user = userService.findByUsername(username);
        if(!user.isEnabled())
        {
            return ResponseDTO.<TokenResponseDTO>builder()
                    .status(403)
                    .whoDidIt(username)
                    .msg("Tài khoản chưa được active!")
                    .build();
        }
        else {
            Date now = new Date();
            Date exp = new Date(now.getTime() + 10 * 60 * 1000);
            String accessToken = jwtTokenService.createToken(username, authorities);
            String refreshToken = jwtTokenService.createRefreshToken(username, authorities);

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            tokenResponseDTO.setAccessTokenExpired(exp);
            tokenResponseDTO.setAccessToken(accessToken);
            tokenResponseDTO.setRefreshToken(refreshToken);
            tokenResponseDTO.setFullName(user.getFullName());
            tokenResponseDTO.setUserName(username);
            return ResponseDTO.<TokenResponseDTO>builder()
                    .status(200)
                    .data(tokenResponseDTO)
                    .build();
        }

    }
    @PostMapping("/access-token")
    public ResponseDTO<TokenResponseDTO> refreshAccessToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        // Trích xuất refreshToken từ header Authorization
        String refreshToken = extractRefreshTokenFromHeader(refreshTokenHeader);

        Date now = new Date();
        Date exp = new Date(now.getTime() + 10 * 60 * 1000);

        // Tiếp tục xử lý như trước
        String newAccessToken = jwtTokenService.refreshAccessToken(refreshToken);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(newAccessToken);
        tokenResponseDTO.setAccessTokenExpired(exp);

        if (newAccessToken != null) {
            return ResponseDTO.<TokenResponseDTO>builder()
                    .status(200)
                    .data(tokenResponseDTO)
                    .build();
        } else {
            // Trả về thông báo lỗi nếu không cấp được access token mới từ refresh token
            return ResponseDTO.<TokenResponseDTO>builder()
                    .status(401)
                    .msg("Invalid refresh token.")
                    .build();
        }
    }
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public CurrentUser me(Principal p,
                           // doc userdetails tra ve tu ham
                           // userDetailsService.loadUserByUsername(username);
                           // da hinh ep kieu ve loai class con
                           @AuthenticationPrincipal CurrentUser user) {
//		String username = p.getName();
//		UserDTO user = userService.findByUsername(username);
//		return user;
//		return (LoginUserDTO) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
        return user;
    }
//    @GetMapping("/me")
//    @PreAuthorize("isAuthenticated()")
//    public CurrentUser me(Principal p) {
//        return (CurrentUser) p;
//    }
    private String extractRefreshTokenFromHeader(String refreshTokenHeader) {
        // Loại bỏ phần tử "Bearer " trong header để chỉ lấy refreshToken
        return refreshTokenHeader.replace("Bearer ", "");
    }
}
