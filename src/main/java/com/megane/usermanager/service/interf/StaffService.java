package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    void create(StaffDTO staffDTO);
    //    void update(CustomerDTO customerDTO);
    void delete(int id);
    StaffDTO getById(int id);
    PageDTO<List<StaffDTO>> search(SearchDTO searchDTO);
    List<StaffDTO> getAll();
}
