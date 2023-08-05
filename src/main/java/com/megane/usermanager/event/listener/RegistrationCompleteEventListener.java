package com.megane.usermanager.event.listener;

import com.megane.usermanager.entity.User;
import com.megane.usermanager.event.RegistrationCompleteEvent;
import com.megane.usermanager.service.interf.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author Sampson Alfred
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

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
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // 1. Get the newly registered user
        theUser = event.getUser();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        userService.saveUserVerificationToken(theUser, verificationToken);
        //4 Build the verification url to be sent to the user
//        String url = event.getApplicationUrl()+"/api/customer/verifyEmail?token="+verificationToken;
        String url = verificationToken;
        //5. Send the email.
        try {
            sendVerificationEmail(theUser,url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }


    public void sendVerificationEmail(User user,String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "\\uD83D\\uDE80 Kích hoạt tài khoản !";
        String senderName = "Cổng dịch vụ BookBoot";
//        String mailContent = "<p> Xin chào!, "+ theUser.getFullName()+ ", </p>"+
//                "<p>Cảm ơn bạn đã đăng ký,"+"" +
//                "Hãy nhấn vào đường link để hoàn tất đăng ký.</p>"+
//                "<a href=\"" +url+ "\">Xác thực!</a>"+
//                "<p> Cảm ơn bạn! <br> Cổng đăng ký người dùng";
//        MimeMessage message = mailSender.createMimeMessage();
//        var messageHelper = new MimeMessageHelper(message);
//        messageHelper.setFrom("caffeegg998@gmail.com", senderName);
//        messageHelper.setTo(theUser.getEmail());
//        messageHelper.setSubject(subject);
//        messageHelper.setText(mailContent, true);
//        mailSender.send(message);

        Context context = new Context();
        context.setVariable("name",user.getFullName());
        context.setVariable("content",url);
        String html = templateEngine.process("xacthuc",context);
        MimeMessage message = mailSender.createMimeMessage();

        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("caffeegg998@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        mailSender.send(message);
        //Kafka send
//        MessageDTO messageDTO = new MessageDTO();
//        messageDTO.setTo(theUser.getEmail());
//        messageDTO.setToName(theUser.getFullName());
//        messageDTO.setSubject("\uD83D\uDE80 Kích hoạt tài khoản !");
//        messageDTO.setContent(html);

//        kafkaTemplate.send("notification",messageDTO);

    }
}
