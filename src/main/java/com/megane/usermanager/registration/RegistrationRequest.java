package com.megane.usermanager.registration;

import java.util.Date;
import java.util.List;

/**
 * @author Sampson Alfred
 */

public record RegistrationRequest(
        String customerCode,
        String fullName,
        String username,
        String password,
        String phoneNumber,
        Date birthDate,
        String homeAddress,
        List<String> roles
)
{
}
