package flab.gumipayments.infrastructure.sender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
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
    public void sendSignupRequest(String toAddress, String acceptKey) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setFrom(id + "@naver.com");
            mimeMessageHelper.setSubject("[구미 페이먼츠] 회원가입 인증 코드");
            mimeMessageHelper.setText("테스트 : " + acceptKey, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("error = ",e);
            throw new RuntimeException();
        }
    }
}
