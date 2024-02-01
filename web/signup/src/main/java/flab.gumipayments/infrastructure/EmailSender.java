package flab.gumipayments.infrastructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender implements Sender {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String id;

    @Override
    public void sendSignupRequest(String email, String signupKey) {
        sendRequest(message -> {
            message.setFrom(id + "@naver.com");
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("[구미 페이먼츠] 회원가입 인증 코드");
            String body = "";
            body += "구미 페이먼츠 인증 코드입니다." + "<br>";
            body += "<a href=\"https://localhost:3000/api/accept/signup?signupKey=" + signupKey + "\" >가입 인증</a>";
            body += "<br>" + "지금 받은 이메일로 구미 페이먼츠를 가입하신 적이 없으면, 이메일을 무시해주세요.";
            message.setText(body, "UTF-8", "html");
        });
    }

    @Override
    public void sendApiKeyRenewRequest(String email, LocalDateTime expireDate) {
        sendRequest(message -> {
            message.setFrom(id + "@naver.com");
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("[구미 페이먼츠] 실서비스용 api 만료 기간 안내");
            String body = "실서비스용 api 만료 기간이 " + expireDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "로 연장되었습니다.";
            message.setText(body, "UTF-8", "html");
        });
    }

    private void sendRequest(MessageCallBack messageCallBack){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            messageCallBack.setMimeMessage(message);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("error = ", e);
            throw new RuntimeException("mime message를 확인해주세요.");
        }
    }

    interface MessageCallBack {
        void setMimeMessage(MimeMessage message) throws MessagingException;
    }
}
