package com.megane.usermanager.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sampson Alfred
 */

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
