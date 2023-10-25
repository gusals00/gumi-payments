package flab.gumipayments.infrastructure.sender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @Mock
    JavaMailSender javaMailSender;
    @InjectMocks
    EmailSender emailSender;

    @Test
    @DisplayName("인증 메일 발송")
    void sendEmail() throws MessagingException {
        String toAddress="123@naver.com";
        String signupKey = "12345";
        MimeMessage message = mock(MimeMessage.class);
        doNothing().when(message).setFrom(anyString());
        doNothing().when(message).setRecipients(any(),anyString());
        doNothing().when(message).setSubject(anyString());
        doNothing().when(message).setText(anyString(),anyString(),anyString());
        when(javaMailSender.createMimeMessage()).thenReturn(message);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        emailSender.sendSignupRequest(toAddress,signupKey);

        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(any(MimeMessage.class));
    }
}