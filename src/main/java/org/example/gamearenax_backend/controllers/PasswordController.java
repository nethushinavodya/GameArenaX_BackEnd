package org.example.gamearenax_backend.controllers;

import org.example.gamearenax_backend.dto.ResponseDTO;
import org.example.gamearenax_backend.service.impl.UserServiceImpl;
import org.example.gamearenax_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("api/v1/password")
@CrossOrigin
public class PasswordController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/sendOtp")
    public ResponseEntity<ResponseDTO> sendOtp(@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            System.out.println(email);
            boolean isExists = userServiceImpl.ifEmailExists(email);
            if (!isExists){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Email Not Found",null));
            }
            int code = (1000 + (int) (Math.random() * 9000));

            sendEmail(email, code);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "Success", code));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void sendEmail(String email, int code) {
        new Thread(() -> {
            try {
                String senderEmail = "nethushiperera03@gmail.com";
                String senderPassword = "akhtgzikhibtvmdf"; // Replace with the app-specific password from Gmail

                String subject = "OTP Code from GameArenaX";

                String body = "Dear User,\n\n" +
                        "Your OTP code for accessing GameArenaX services is: " + code + "\n\n" +
                        "Please use this code to verify your identity. The OTP is valid for 10 minutes only.\n" +
                        "If you did not request this OTP, please ignore this email or contact support.\n\n" +
                        "Best regards,\n" +
                        "The SmallWorld Team";

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                // Create a mail session with authentication
                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    message.setSubject(subject);
                    message.setText(body);

                    Transport.send(message);

                    System.out.println("OTP sent successfully to " + email);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
