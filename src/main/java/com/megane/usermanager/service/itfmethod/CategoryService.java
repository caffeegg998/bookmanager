package com.megane.usermanager.service.itfmethod;

import com.megane.usermanager.dto.CategoryDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.SearchDTO;


import java.util.List;


public interface CategoryService {
    void create(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(int id);

    CategoryDTO getById(int id);

    PageDTO<List<CategoryDTO>> search(SearchDTO searchDTO);
    List<CategoryDTO> getAll();
}
