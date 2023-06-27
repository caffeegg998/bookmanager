package com.megane.usermanager.service.implmethod;

import com.megane.usermanager.dto.CustomerDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.entity.Customer;
import com.megane.usermanager.repo.CustomerRepo;
import com.megane.usermanager.service.itfmethod.CustomerService;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public void create(CustomerDTO customerDTO) {
       Customer customer = new ModelMapper().map(customerDTO,Customer.class);
       customerRepo.save(customer);
    }

//    @Override
//    public void update(CustomerDTO customerDTO) {
//        Customer currentCustomer = customerRepo.findById(customerDTO.getUser().getId()).orElseThrow(NoResultException::new);
//        if(currentCustomer !=null)
//        {
//            currentCustomer.setCustomerCode(currentCustomer.getCustomerCode());
//            currentCustomer.getUser().setPhoneNumber(currentCustomer.getUser().getPhoneNumber());
//            currentCustomer.getUser().setEmail(currentCustomer.getUser().getEmail());
//            currentCustomer.getUser().setUsername(currentCustomer.getUser().getUsername());
//            currentCustomer.getUser().setFullName(currentCustomer.getUser().getFullName());
//            currentCustomer.getUser().setHomeAddress(currentCustomer.getUser().getHomeAddress());
//            currentCustomer.getUser().setBirthDate(currentCustomer.getUser().getBirthDate());
//            currentCustomer.getUser().setPassword(currentCustomer.getUser().getPassword());
//
//        }
//    }
    @Override
    public void delete(int id) {
        customerRepo.deleteById(id);
    }

    @Override
    public CustomerDTO getById(int id) {
        Customer customer = customerRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(customer);
    }
    public CustomerDTO convert(Customer customer){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(customer,CustomerDTO.class);
    }

    @Override
    public PageDTO<List<CustomerDTO>> search(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("customerCode").ascending();

        if (StringUtils.hasText(searchDTO.getSortedField())) {
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }

        if (searchDTO.getCurrentPage() == null)
            searchDTO.setCurrentPage(0);

        if (searchDTO.getSize() == null)
            searchDTO.setSize(5);

        if (searchDTO.getKeyword() == null)
            searchDTO.setKeyword("");

        PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);

        Page<Customer> page = customerRepo.searchCustomer("%" + searchDTO.getKeyword() + "%", pageRequest);

        PageDTO<List<CustomerDTO>> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());


        List<CustomerDTO> customerDTOS = page.get().map(cu -> convert(cu)).collect(Collectors.toList());
        pageDTO.setData(customerDTOS);

        log.info("PageDTO totalPages {} PageDTO {}  ",pageDTO.getTotalPages(),pageDTO);
        System.out.println("PageDTO: abc "+ pageDTO);
        return pageDTO;
    }

    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customerList =  customerRepo.findAll();
        return customerList.stream().map(cu -> convert(cu)).collect(Collectors.toList());
    }
}
