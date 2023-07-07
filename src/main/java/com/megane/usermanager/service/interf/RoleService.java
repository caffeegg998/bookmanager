package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    void create(RoleDTO roleDTO);
    void update(RoleDTO roleDTO);
    void delete(int id);
    List<RoleDTO> getAll();
}
