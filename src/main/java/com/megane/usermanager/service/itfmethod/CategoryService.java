package com.megane.usermanager.service.itfmethod;

import com.megane.usermanager.dto.CategoryDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.entity.Category;
import com.megane.usermanager.repo.CategoryRepo;
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


public interface CategoryService {
    void create(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(int id);

    CategoryDTO getById(int id);

    PageDTO<List<CategoryDTO>> search(SearchDTO searchDTO);
    List<CategoryDTO> getAll();
}
