package com.megane.usermanager.registration.password;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String passwordResetToken);
    PasswordResetToken findAllByUserId(int id);

}

