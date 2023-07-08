package com.megane.usermanager.controller;

import com.megane.usermanager.dto.*;
import com.megane.usermanager.entity.Customer;
import com.megane.usermanager.event.RegistrationCompleteEvent;
import com.megane.usermanager.registration.token.VerificationToken;
import com.megane.usermanager.registration.token.VerificationTokenRepository;
import com.megane.usermanager.service.interf.CustomerService;
import com.megane.usermanager.service.interf.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    UserService userService;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    VerificationTokenRepository tokenRepository;
    @PostMapping("/")
    public ResponseDTO<Void> createRegister(@RequestBody @Valid CustomerDTO customerDTO, final HttpServletRequest request){
        Customer customer = customerService.create(customerDTO);
        publisher.publishEvent(new RegistrationCompleteEvent(customer.getUser(), applicationUrl(request)));
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("ok")
                .build();
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return "Tài khoản này đã được active!";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Hoàn thành xác thực Email!. Bạn có thể đăng nhập vào website!";
        }
        return "Mã xác thực không chính xác!";
    }
    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
    @GetMapping("/") // ?id=1000
    @ResponseStatus(code = HttpStatus.OK)
    // @Secured({"ROLE_ADMIN","ROLE_SYSADMIN"}) //ROLE_   //hAI dONG NAY GIONG NHAU //Bao mat tren ham
    // @RolesAllowed({"ROLE_ADMIN","ROLE_SYSADMIN"})		  //
    // @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")		  //
    //@PreAuthorize("isAuthenticated()")			      //
    public ResponseDTO<CustomerDTO> get(
            @RequestParam("id") int id) {
        return ResponseDTO.<CustomerDTO>builder()
                .status(200)
                .msg("OK!")
                .data(customerService.getById(id))
                .build();
    }

    @GetMapping("/list")
    public ResponseDTO<List<CustomerDTO>> list() {
        List<CustomerDTO> departmentDTOs = customerService.getAll();
        return ResponseDTO.<List<CustomerDTO>>builder().status(200).data(departmentDTOs).build();
    }
//    @PutMapping("/")
//    public ResponseDTO<Void> edit(@RequestParam @Valid CustomerDTO customerDTO){
//        customerService.update(customerDTO);
//        return ResponseDTO.<Void>builder()
//                .status(200)
//                .msg("ok")
//                .build();
//
//    }
    @PostMapping("/search") // jackson
    public ResponseDTO<PageDTO<List<CustomerDTO>>>
    search(@RequestBody @Valid SearchDTO searchDTO) {
        PageDTO<List<CustomerDTO>> pageDTO = customerService.search(searchDTO);

        return ResponseDTO.
                <PageDTO<List<CustomerDTO>>>builder()
                .status(200)
                .data(pageDTO)
                .build();
    }
}
