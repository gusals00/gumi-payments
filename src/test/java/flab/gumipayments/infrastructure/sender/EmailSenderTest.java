package flab.gumipayments.infrastructure.sender;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @Mock
    JavaMailSender javaMailSender;
    @InjectMocks
    EmailSender sut;

    private String toAddress;
    private String signupKey;
    private MimeMessage message;

    @BeforeEach
    void setup() {
        toAddress = "123@naver.com";
        signupKey = "12345";
        message = mock(MimeMessage.class);
    }

    @Test
    @DisplayName("예외: 메일 형식이 올바르지 않으면 메일 발송이 실패한다.")
    void sendEmailFail() {
        toAddress = "123";
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doThrow(MailSendException.class).when(javaMailSender).send(any(MimeMessage.class));

        assertThatThrownBy(() -> sut.sendSignupRequest(toAddress, signupKey))
                .isInstanceOf(MailException.class);
        verify(javaMailSender).createMimeMessage();
    }

    @Test
    @DisplayName("성공: 가입 요청 인증 이메일 발송을 성공한다.")
    void sendEmail() {
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        sut.sendSignupRequest(toAddress, signupKey);

        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(any(MimeMessage.class));
    }

}