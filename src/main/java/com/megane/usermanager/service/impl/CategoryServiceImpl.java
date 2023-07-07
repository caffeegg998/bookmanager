package com.megane.usermanager.service.impl;

import com.megane.usermanager.dto.CategoryDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.entity.Category;
import com.megane.usermanager.repo.CategoryRepo;
import com.megane.usermanager.service.interf.CategoryService;
import jakarta.persistence.NoResultException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public void create(CategoryDTO categoryDTO) {
        Category category = new ModelMapper().map(categoryDTO,Category.class);
        categoryRepo.save(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryRepo.findById(categoryDTO.getId()).orElse(null);
        if (category != null){
            category.setName(categoryDTO.getName());
            categoryRepo.save(category);
        }
    }

    @Override
    public void delete(int id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public CategoryDTO getById(int id) {
        Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);
        return convert(category);
    }
    private CategoryDTO convert(Category category){
        return new ModelMapper().map(category,CategoryDTO.class);
    }

    @Override
    public PageDTO<List<CategoryDTO>> search(SearchDTO searchDTO) {
        Sort sortBy = Sort.by("name").ascending();

        if (StringUtils.hasText(String.valueOf(searchDTO.getSortedField()))) {
            sortBy = Sort.by(String.valueOf(searchDTO.getSortedField())).ascending();
        }

        if (searchDTO.getCurrentPage() == null)
            searchDTO.setCurrentPage(0);

        if (searchDTO.getSize() == null)
            searchDTO.setSize(5);

        if (searchDTO.getKeyword() == null)
            searchDTO.setKeyword("");

        PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);

        Page<Category> page = categoryRepo.searchCategory("%" + searchDTO.getKeyword() + "%", pageRequest);

        PageDTO<List<CategoryDTO>> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());


        List<CategoryDTO> departmentDTOs = page.get().map(c -> convert(c)).collect(Collectors.toList());
        pageDTO.setData(departmentDTOs);
        return pageDTO;
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> departmentList = categoryRepo.findAll();

//		List<UserDTO> userDTOs = new ArrayList<>();
//		for (User user : userList) {
//			userDTOs.add(convert(user));
//		}
//
//		return userDTOs;
        // java 8
        return departmentList.stream().map(c -> convert(c))
                .collect(Collectors.toList());
    }
}
