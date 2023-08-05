package com.megane.usermanager.controller;

import com.megane.usermanager.Jwt.JwtTokenService;
import com.megane.usermanager.dto.*;
import com.megane.usermanager.dto.kafka.MessageDTO;
import com.megane.usermanager.dto.kafka.StatisticDTO;
import com.megane.usermanager.entity.Customer;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.event.PasswordRequestEvent;
import com.megane.usermanager.event.RegistrationCompleteEvent;
import com.megane.usermanager.event.listener.RegistrationCompleteEventListener;
import com.megane.usermanager.registration.password.PasswordResetRequest;
import com.megane.usermanager.registration.password.PasswordResetToken;
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

    @Autowired
    JwtTokenService jwtTokenService;
    @PostMapping("/register")
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
                .msg("Đăng ký thành công")
                .build();
    }
    @GetMapping("/verifyEmail")
    public AuthResponseDTO<Void> verifyEmail(@RequestParam("token") String token,HttpServletRequest request){
        VerificationToken theToken = tokenRepository.findByToken(token);
       if(theToken != null){
           if (theToken.getUser().isEnabled()){
               return AuthResponseDTO.<Void>builder()
                       .status(201)
                       .msg("200")
                       .complete("Tài khoản đã được active!Bạn có thể đăng nhập.")
                       .build();
           }
       }


        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return AuthResponseDTO.<Void>builder()
                    .status(200)
                    .msg("200")
                    .complete("Hoàn thành xác thực Email!. Bạn có thể đăng nhập vào website!")
                    .build();
        }
        if (verificationResult.equalsIgnoreCase("Token already expired"))
        {
            return AuthResponseDTO.<Void>builder()
                    .status(403)
                    .msg("Mã xác thực đã hết hạn")
                    .resendEmail(applicationUrl(request)+"/api/customer/resend-verification-token/?token="+token)
                    .build();
        }
        if (verificationResult.equalsIgnoreCase("Invalid verification token"))
        {
            return AuthResponseDTO.<Void>builder()
                    .status(404)
                    .msg("Mã xác thực không chính xác")
                    .build();
        }
        return null;

    }

    @GetMapping("/resend-verification-token/")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken =  userService.generateNewVerificationToken(oldToken);
        User theUser = verificationToken.getUser();
        resendVerificationTokenEmail(theUser, applicationUrl(request), verificationToken);
        return "A new verification link hs been sent to your email," +
                " please, check to complete your registration";
    }
    private void resendVerificationTokenEmail(User theUser, String applicationUrl, VerificationToken token)
            throws MessagingException, UnsupportedEncodingException {
//        String url = applicationUrl+"/register/verifyEmail?token="+token.getToken(); //send link
        String url = token.getToken();
        eventListener.sendVerificationEmail(theUser,url);
        log.info("Click the link to verify your registration :  {}", url);
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
    public AuthResponseDTO<Void> resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {

        Optional<User> user = userService.findByEmail(passwordResetRequest.getEmail());
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);

            return AuthResponseDTO.<Void>builder()
                    .status(200)
                    .complete("Yêu cầu đã được xử lý. Vui lòng kiểm tra email! ")
                    .build();
        }

        return AuthResponseDTO.<Void>builder()
                .status(404)
                .msg("Email không chính xác")
                .build();

    }

    private String passwordResetEmailLink(User user, String applicationUrl,
                                          String passwordToken) throws MessagingException, UnsupportedEncodingException {
//        String url = applicationUrl+"/api/customer/reset-password?token="+passwordToken; //send link
        String url = passwordToken;

        publisher.publishEvent(new PasswordRequestEvent(user,url));
        return url;
    }
    @PostMapping("/reset-password")
    public AuthResponseDTO<Void> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String token){
        String tokenVerificationResult = userService.validatePasswordResetToken(token);
        if (tokenVerificationResult.equalsIgnoreCase("valid")) {
            Optional<User> theUser = Optional.ofNullable(userService.findUserByPasswordToken(token));
            if (theUser.isPresent()) {
                userService.resetPassword(theUser.get(), passwordResetRequest.getNewPassword());
                return AuthResponseDTO.<Void>builder()
                        .status(201)
                        .msg("Mật khẩu của bạn đã được thay đổi.")
                        .build();
            }
        }
        if (tokenVerificationResult.equalsIgnoreCase("Link already expired, resend link")) {
            return AuthResponseDTO.<Void>builder()
                    .status(419)
                    .msg("Mã thay đổi mật khẩu đã hết hạn, vui lòng gửi lại yêu cầu đổi mật khẩu!")
                    .build();
        }
        return AuthResponseDTO.<Void>builder()
                .status(498)
                .msg("Mã thay đổi mật khẩu không chính xác!")
                .build();
    }
    @GetMapping("/resend-reset-password-token/")
    public String resendResetPasswordToken(@RequestParam("token") String oldToken, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        PasswordResetToken passwordResetToken =  userService.generateNewResetPasswordToken(oldToken);
        User theUser = passwordResetToken.getUser();
        resendResetPasswordTokenEmail(theUser, applicationUrl(request), passwordResetToken);
        return "A new reset password link hs been sent to your email," +
                " please, check to complete your request";
    }
    private void resendResetPasswordTokenEmail(User user, String applicationUrl,PasswordResetToken passwordToken)
            throws MessagingException, UnsupportedEncodingException {
        String url = passwordToken.getToken().toString();

        publisher.publishEvent(new PasswordRequestEvent(user,url));
    }
    @GetMapping("/get-me/")
    public ResponseDTO<UserDTO> getMyselfById(@RequestHeader("Authorization") String authorizationHeader){
        String bearerToken = authorizationHeader.replace("Bearer ", "");
        String username = jwtTokenService.getUsername(bearerToken);
        return ResponseDTO.<UserDTO>builder().status(200).data(userService.findByUsername(username)).build();
    }
}
