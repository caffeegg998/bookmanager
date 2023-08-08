package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.registration.RegistrationRequest;
import com.megane.usermanager.registration.password.PasswordResetToken;
import com.megane.usermanager.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;


public interface UserService {
    void create(UserDTO userDTO);
    void update(UserDTO userDTO);
    void delete(int id);
    List<UserDTO> getAll();
    PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO);
    UserDTO getById(int id);
    UserDTO findByUsername(String username);

    //ACTIVE USER BY TOKEN
    void saveUserVerificationToken(User theUser, String token);
    String validateToken(String theToken);

    Optional<User> findByEmail(String email);

    //REQUEST PASSWORD RESET
    void resetPassword(User theUser, String newPassword);

    String validatePasswordResetToken(String token);

    User findUserByPasswordToken(String token);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);
    void createActiveTokenForUser(User user, String activeToken);
    VerificationToken generateNewVerificationToken(String oldToken);
    PasswordResetToken generateNewResetPasswordToken(String oldToken);
}
