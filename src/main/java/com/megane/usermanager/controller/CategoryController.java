package com.megane.usermanager.controller;

import com.megane.usermanager.dto.CategoryDTO;
import com.megane.usermanager.dto.PageDTO;
import com.megane.usermanager.dto.ResponseDTO;
import com.megane.usermanager.dto.SearchDTO;
import com.megane.usermanager.service.interf.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmanager/")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseDTO<Void> create(@ModelAttribute @Valid CategoryDTO categoryDTO){
        categoryService.create(categoryDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("ok").build();
    }
//    @PostMapping("/json")
//    public ResponseDTO<Void> createNewJSON(
//            @RequestBody @Valid CategoryDTO categoryDTO) {
//        categoryService.create(categoryDTO);
//        return ResponseDTO.<Void>builder().status(200)
//                .msg("ok").build();
//    }

    @DeleteMapping("/delete-category/") // ?id=1000
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseDTO<Void> delete(@RequestParam("id") int id) {
        categoryService.delete(id);
        return ResponseDTO.<Void>builder().status(200)
                .msg("ok").build();
    }

    @GetMapping("/get-category/") // ?id=1000
    @ResponseStatus(code = HttpStatus.OK)
    // @Secured({"ROLE_ADMIN","ROLE_SYSADMIN"}) //ROLE_   //hAI dONG NAY GIONG NHAU //Bao mat tren ham
    // @RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})		  //
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")		  //
    //@PreAuthorize("isAuthenticated()")			      //
    public ResponseDTO<CategoryDTO> get(
            @RequestParam("id") int id) {
        return ResponseDTO.<CategoryDTO>builder()
                .status(200)
                .msg("OK!")
                .data(categoryService.getById(id))
                .build();
    }

    @GetMapping("/list-category")
    // @RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})		  //
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseDTO<List<CategoryDTO>> list() {
        List<CategoryDTO> departmentDTOs = categoryService.getAll();
        return ResponseDTO.<List<CategoryDTO>>builder().status(200).data(departmentDTOs).build();
    }

    @PutMapping("/edit-category/")
    public ResponseDTO<CategoryDTO> edit(@RequestParam @Valid CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return ResponseDTO.<CategoryDTO>builder()
                .status(200)
                .data(categoryDTO)
                .build();
    }

    @PostMapping("/search-category") // jackson
    public ResponseDTO<PageDTO<List<CategoryDTO>>>
    search(@RequestBody @Valid SearchDTO searchDTO) {
        PageDTO<List<CategoryDTO>> pageDTO = categoryService.search(searchDTO);

        return ResponseDTO.
                <PageDTO<List<CategoryDTO>>>builder()
                .status(200)
                .data(pageDTO)
                .build();
    }
}
