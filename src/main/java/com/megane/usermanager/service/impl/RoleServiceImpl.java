package com.megane.usermanager.service.impl;

import com.megane.usermanager.dto.RoleDTO;
import com.megane.usermanager.entity.Role;
import com.megane.usermanager.repo.RoleRepo;
import com.megane.usermanager.service.interf.RoleService;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;
    @Override
    public void create(RoleDTO roleDTO) {
        Role role = new ModelMapper().map(roleDTO,Role.class);
        roleRepo.save(role);
    }
    @Override
    public void update(RoleDTO roleDTO) {
        Role currentRole = roleRepo.findById(roleDTO.getId()).orElseThrow(NoResultException::new);
        if(currentRole != null){
            currentRole.setName(roleDTO.getName());
            roleRepo.save(currentRole);
        }
    }

    @Override
    public void delete(int id) {
        roleRepo.deleteById(id);
    }
    private RoleDTO convert(Role role){

        return new ModelMapper().map(role, RoleDTO.class);
    }
    @Override
    public List<RoleDTO> getAll() {
        List<Role> roleList = roleRepo.findAll();
        return roleList.stream().map(cu -> convert(cu)).collect(Collectors.toList());
    }
}
