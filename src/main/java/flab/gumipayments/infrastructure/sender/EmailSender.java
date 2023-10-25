package flab.gumipayments.infrastructure.sender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender implements Sender {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String id;

    @Override
    public void sendSignupRequest(String toAddress, String signupKey) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(id+"@naver.com");
            message.setRecipients(MimeMessage.RecipientType.TO, toAddress);
            message.setSubject("[구미 페이먼츠] 회원가입 인증 코드");
            String body = "";
            body += "구미 페이먼츠 인증 코드입니다." + "<br>";
            body +="<a href=\"https://localhost:3000/api/accept/signup?signupKey="+signupKey+"&email="+toAddress+"\" >가입 인증</a>";
            body += "<br>" +"지금 받은 이메일로 구미 페이먼츠를 가입하신 적이 없으면, 이메일을 무시해주세요.";

            message.setText(body,"UTF-8", "html");
            javaMailSender.send(message);
        } catch (MessagingException | MailException e) {
            log.error("error = ",e);
            throw new RuntimeException("다시 시도해주세요.");
        }
    }
}
