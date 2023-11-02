package flab.gumipayments.application;

import flab.gumipayments.infrastructure.sender.Sender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcceptRequesterApplicationTest {
    @InjectMocks
    AcceptRequesterApplication sut;

    @Mock
    Sender sender;

    @Test
    @DisplayName("성공 : 가입 요청 인증 이메일 발송을 성공한다.")
    void requestSignupAccept() {
        String toAddress ="123@naver.com";
        String signupKey = "key";

        sut.requestSignupAccept(toAddress,signupKey);

        verify(sender).sendSignupRequest(toAddress,signupKey);
    }
}