package flab.gumipayments.infrastructure;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private EmailSender sut;

    private String email;
    private String signupKey;
    private MimeMessage message;

    @BeforeEach
    void setup() {
        email = "123@naver.com";
        signupKey = "12345";
        message = mock(MimeMessage.class);
    }

    @Test
    @DisplayName("성공: 가입 요청 인증 이메일 발송을 성공한다.")
    void sendEmail() {
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        sut.sendSignupRequest(email, signupKey);

        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(any(MimeMessage.class));
    }

}