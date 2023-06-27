package com.megane.usermanager.service.itfmethod;

import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.repo.UserRepo;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public interface UserService {
    void create(UserDTO userDTO);
    void update(UserDTO userDTO);
    void delete(int id);
    List<UserDTO> getAll();
    PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO);

    UserDTO getById(int id);
}
