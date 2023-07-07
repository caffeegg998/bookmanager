//package com.megane.usermanager.controller;
//
//import com.megane.usermanager.dto.CategoryDTO;
//import com.megane.usermanager.dto.ResponseDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController("/api/bookstoremannager/")
//public class BookStoreController {
//
//    @GetMapping("/admin/") // ?id=1000
//    @ResponseStatus(code = HttpStatus.OK)
//    // @Secured({"ROLE_ADMIN","ROLE_SYSADMIN"}) //ROLE_   //hAI dONG NAY GIONG NHAU //Bao mat tren ham
//    // @RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})		  //
//    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")		  //
//    //@PreAuthorize("isAuthenticated()")			      //
//    public ResponseDTO<Void> get(){
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{/api/usermanager/}")
//                .buildAndExpand(newId)
//                .toUri();
//
//        // Trả về redirect với mã HTTP 302 Found
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .location(location)
//                .build();
//    }
//}
