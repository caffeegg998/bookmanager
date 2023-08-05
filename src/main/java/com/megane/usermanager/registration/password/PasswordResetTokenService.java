package com.megane.usermanager.registration.password;

import com.megane.usermanager.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;


    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        PasswordResetToken existingToken = passwordResetTokenRepository.findAllByUserId(user.getId());
        if (existingToken != null) {
            // Nếu đã tồn tại bản ghi, cập nhật thông tin của nó
            existingToken.setToken(passwordToken);
            existingToken.setExpirationTime(existingToken.getTokenExpirationTime());
            passwordResetTokenRepository.save(existingToken);
        } else {
            // Nếu chưa tồn tại bản ghi, tạo một bản ghi mới
            PasswordResetToken passwordRestToken = new PasswordResetToken(passwordToken, user);
            passwordResetTokenRepository.save(passwordRestToken);
        }

    }

    public String validatePasswordResetToken(String passwordResetToken) {
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(passwordResetToken);
        if(passwordToken == null){
            return "Invalid verification token";
        }
        User user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "Link already expired, resend link";

        }
        return "valid";
    }
    public Optional<User> findUserByPasswordToken(String passwordResetToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
    }

    public PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token);
    }
}
