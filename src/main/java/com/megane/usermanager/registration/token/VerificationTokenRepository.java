package com.megane.usermanager.registration.token;

import com.megane.usermanager.registration.password.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sampson Alfred
 */

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findAllByUserId(int id);
}
