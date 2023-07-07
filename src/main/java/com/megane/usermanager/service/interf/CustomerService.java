package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.CustomerDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;

import java.util.List;


public interface CustomerService {
    void create(CustomerDTO customerDTO);
//    void update(CustomerDTO customerDTO);
    void delete(int id);
    CustomerDTO getById(int id);
    PageDTO<List<CustomerDTO>> search(SearchDTO searchDTO);
    List<CustomerDTO> getAll();
}
