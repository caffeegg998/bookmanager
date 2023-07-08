package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.registration.RegistrationRequest;

import java.util.List;


public interface UserService {
    void create(UserDTO userDTO);
    void update(UserDTO userDTO);
    void delete(int id);
    List<UserDTO> getAll();
    PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO);
    UserDTO getById(int id);
    UserDTO findByUsername(String username);
    void saveUserVerificationToken(User theUser, String token);
    String validateToken(String theToken);
}
