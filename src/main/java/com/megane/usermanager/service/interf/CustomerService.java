package com.megane.usermanager.service.interf;

import com.megane.usermanager.dto.CustomerDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.dto.UserDTO;
import com.megane.usermanager.entity.Customer;

import java.util.List;


public interface CustomerService {
    Customer create(CustomerDTO customerDTO);
//    void update(CustomerDTO customerDTO);
    void delete(int id);
    CustomerDTO getById(int id);
    PageDTO<List<CustomerDTO>> search(SearchDTO searchDTO);
    List<CustomerDTO> getAll();
    CustomerDTO findByUsername(String username);
}
