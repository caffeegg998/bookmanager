//package com.megane.usermanager.registration;
//
//import com.megane.usermanager.entity.User;
//import com.megane.usermanager.registration.token.VerificationTokenRepository;
//import com.megane.usermanager.service.interf.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author Sampson Alfred
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/register")
//public class RegistrationController {
//
//    @Autowired
//    UserService userService;
//    private final ApplicationEventPublisher publisher;
//    private final VerificationTokenRepository tokenRepository;
//
//    @PostMapping
//    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){
//        User user = userService.create(registrationRequest);
//        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
//        return "Success!  Please, check your email for to complete your registration";
//    }
//
//    @GetMapping("/verifyEmail")
//    public String verifyEmail(@RequestParam("token") String token){
//        VerificationToken theToken = tokenRepository.findByToken(token);
//        if (theToken.getUser().isEnabled()){
//            return "This account has already been verified, please, login.";
//        }
//        String verificationResult = userService.validateToken(token);
//        if (verificationResult.equalsIgnoreCase("valid")){
//            return "Email verified successfully. Now you can login to your account";
//        }
//        return "Invalid verification token";
//    }
//
//
//
//    public String applicationUrl(HttpServletRequest request) {
//        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
//    }
//}
