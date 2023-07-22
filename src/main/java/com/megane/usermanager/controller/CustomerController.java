package com.megane.usermanager.controller;

import com.megane.usermanager.dto.*;
import com.megane.usermanager.dto.kafka.MessageDTO;
import com.megane.usermanager.dto.kafka.StatisticDTO;
import com.megane.usermanager.entity.Customer;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.event.PasswordRequestEvent;
import com.megane.usermanager.event.RegistrationCompleteEvent;
import com.megane.usermanager.event.listener.RegistrationCompleteEventListener;
import com.megane.usermanager.registration.password.PasswordResetRequest;
import com.megane.usermanager.registration.token.VerificationToken;
import com.megane.usermanager.registration.token.VerificationTokenRepository;
import com.megane.usermanager.service.interf.CustomerService;
import com.megane.usermanager.service.interf.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@Slf4j
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

    @Autowired
    RegistrationCompleteEventListener eventListener;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;
    @PostMapping("/")
    public ResponseDTO<Void> createRegister(@RequestBody @Valid CustomerDTO customerDTO, final HttpServletRequest request){
        Customer customer = customerService.create(customerDTO);


        //Kafka
//        StatisticDTO statisticDTO = new StatisticDTO("Account: " + customerDTO.getUser().getEmail() + "is created",new Date());
//        MessageDTO messageDTO = new MessageDTO();
//        messageDTO.setTo(customerDTO.getUser().getEmail());
//        messageDTO.setToName(customerDTO.getUser().getFullName());
//        messageDTO.setSubject("Cai nay la de test Kafka!");
//        messageDTO.setContent("Kafka test la cai nay nay!");
//
//        kafkaTemplate.send("notification",messageDTO);
//        kafkaTemplate.send("statistic",statisticDTO);

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
        if (verificationResult.equalsIgnoreCase("Token already expired"))
        {
            return "Mã xác thực đã hết hạn";
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

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<User> user = userService.findByEmail(passwordResetRequest.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
        }

        return passwordResetUrl;
    }

    private String passwordResetEmailLink(User user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/customer/reset-password?token="+passwordToken;

        publisher.publishEvent(new PasswordRequestEvent(user,url));
        log.info("Click the link to reset your password :  {}", url);
        return url;
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String token){
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<User> theUser = Optional.ofNullable(userService.findUserByPasswordToken(token));
        if (theUser.isPresent()) {
            userService.resetPassword(theUser.get(), passwordResetRequest.getNewPassword());
            return "Password has been reset successfully";
        }
        return "Invalid password reset token";
    }
}
