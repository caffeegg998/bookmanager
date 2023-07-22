package com.megane.usermanager.event.listener;

import com.megane.usermanager.dto.kafka.MessageDTO;
import com.megane.usermanager.entity.User;
import com.megane.usermanager.event.PasswordRequestEvent;
import com.megane.usermanager.event.RegistrationCompleteEvent;
import com.megane.usermanager.service.interf.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordRequestEventListener implements ApplicationListener<PasswordRequestEvent> {

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private SpringTemplateEngine templateEngine;
    private User theUser;
    @Override
    public void onApplicationEvent(PasswordRequestEvent event) {
        theUser = event.getUser();
        String url = event.getApplicationUrl();
        try {
            sendPasswordResetVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);

    }
    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
//        String subject = "Password Reset Request Verification";
//        String senderName = "User Registration Portal Service";
//        String mailContent = "<p> Hi, "+ theUser.getFullName() + ", </p>"+
//                "<p><b>You recently requested to reset your password,</b>"+"" +
//                "Please, follow the link below to complete the action.</p>"+
//                "<a href=\"" + url + "\">Reset password</a>"+
//                "<p> Users Registration Portal Service";
//        MimeMessage message = mailSender.createMimeMessage();
//        var messageHelper = new MimeMessageHelper(message);
//        messageHelper.setFrom("caffeegg998@gmail.com", senderName);
//        messageHelper.setTo(theUser.getEmail());
//        messageHelper.setSubject(subject);
//        messageHelper.setText(mailContent, true);
//        mailSender.send(message);

        Context context = new Context();
        context.setVariable("name",theUser.getFullName());
        context.setVariable("content",url);
        String html = templateEngine.process("passwordreset",context);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(theUser.getEmail());
        messageDTO.setToName(theUser.getFullName());
        messageDTO.setSubject("\uD83D\uDE80 Lấy lại mật khẩu! !");
        messageDTO.setContent(html);

        kafkaTemplate.send("notification",messageDTO);
    }
}
