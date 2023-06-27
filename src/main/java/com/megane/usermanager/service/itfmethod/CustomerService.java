package com.megane.usermanager.service.itfmethod;

import com.megane.usermanager.dto.CustomerDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.entity.Customer;
import com.megane.usermanager.repo.CustomerRepo;
import com.megane.usermanager.repo.UserRepo;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


public interface CustomerService {
    void create(CustomerDTO customerDTO);
//    void update(CustomerDTO customerDTO);
    void delete(int id);
    CustomerDTO getById(int id);
    PageDTO<List<CustomerDTO>> search(SearchDTO searchDTO);
    List<CustomerDTO> getAll();
}
