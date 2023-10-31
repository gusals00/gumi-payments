package flab.gumipayments.infrastructure.sender;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @Mock
    JavaMailSender javaMailSender;
    @InjectMocks
    EmailSender emailSender;

    private String toAddress;
    private String signupKey;
    private MimeMessage message;
    private LocalDateTime now;

    @BeforeEach
    void setup() {
        toAddress = "123@naver.com";
        signupKey = "12345";
        message = mock(MimeMessage.class);
        now = LocalDateTime.now();
    }


    @Test
    @DisplayName("인증 메일 발송 성공")
    void sendEmail() {
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        emailSender.sendSignupRequest(toAddress, signupKey, now);

        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    @DisplayName("인증 메일 발송 실패")
    void sendEmailFail() {
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doThrow(MailSendException.class).when(javaMailSender).send(any(MimeMessage.class));

        assertThatThrownBy(() -> emailSender.sendSignupRequest(toAddress, signupKey, now))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("다시 시도해주세요.");
        verify(javaMailSender).createMimeMessage();
    }
}