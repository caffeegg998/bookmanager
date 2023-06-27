package com.megane.usermanager.service.implmethod;

import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.repo.UserRepo;
import com.megane.usermanager.service.itfmethod.UserService;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public void create(UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO,User.class);
        userRepo.save(user);
    }

    @Override
    public void update(UserDTO userDTO) {
        User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
        if(currentUser != null){
            currentUser.setPassword(userDTO.getPassword());
            currentUser.setEmail(userDTO.getEmail());
            currentUser.setBirthDate(userDTO.getBirthDate());
            currentUser.setUsername(userDTO.getUsername());
            currentUser.setFullName(userDTO.getFullName());
            currentUser.setHomeAddress(userDTO.getHomeAddress());
            userRepo.save(currentUser);
        }

    }

    @Override
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> userList = userRepo.findAll();
        return userList.stream().map(u -> convert(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO) {
        return null;
    }

    @Override
    public UserDTO getById(int id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(user);
    }
    private UserDTO convert(User user){
        return new ModelMapper().map(user,UserDTO.class);
    }
}
