package com.megane.usermanager.service.impl;

import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.dto.StaffDTO;
import com.megane.usermanager.entity.Staff;
import com.megane.usermanager.repo.StaffRepo;
import com.megane.usermanager.service.interf.StaffService;
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
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffRepo staffRepo;

    @Override
    public void create(StaffDTO staffDTO) {
        Staff staff = new ModelMapper().map(staffDTO,Staff.class);
        staffRepo.save(staff);
    }

    @Override
    public void delete(int id) {
        staffRepo.deleteById(id);
    }

    @Override
    public StaffDTO getById(int id) {
        Staff staff = staffRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(staff);
    }

    private StaffDTO convert(Staff staff){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return  modelMapper.map(staff,StaffDTO.class);
    }

    @Override
    public PageDTO<List<StaffDTO>> search(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("staffCode").ascending();

        if (StringUtils.hasText(searchDTO.getSortedField())){
            sortBy = Sort.by(searchDTO.getSortedField()).ascending();
        }
        if (searchDTO.getCurrentPage() == null){
            searchDTO.setCurrentPage(0);
        }
        if (searchDTO.getSize() == null){
            searchDTO.setSize(5);
        }
        if (searchDTO.getKeyword() == null){
            searchDTO.setKeyword("");
        }

        PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(),sortBy);
        Page<Staff> page = staffRepo.searchStaff("%" + searchDTO.getKeyword()+"%",pageRequest);

        PageDTO<List<StaffDTO>> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());

        List<StaffDTO> staffDTOS = page.get().map(st -> convert(st)).collect(Collectors.toList());
        pageDTO.setData(staffDTOS);

        log.info("PageDTO totalPages {} PageDTO {} ",pageDTO.getTotalPages(),pageDTO);

        return pageDTO;

    }

    @Override
    public List<StaffDTO> getAll() {
        List<Staff> staffList = staffRepo.findAll();
        return staffList.stream().map(st -> convert(st)).collect(Collectors.toList());
    }
}
