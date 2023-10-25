package flab.gumipayments.application;

import flab.gumipayments.domain.KeyFactory;
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
    AcceptRequesterApplication acceptRequesterApplication;

    @Mock
    Sender sender;

    @Test
    @DisplayName("sender를 호출한다.")
    void requestSignupAccept() {
        String toAddress ="123@naver.com";
        String signupKey = KeyFactory.generateSignupKey();
        doNothing().when(sender).sendSignupRequest(toAddress,signupKey);

        acceptRequesterApplication.requestSignupAccept(toAddress,signupKey);

        verify(sender).sendSignupRequest(toAddress,signupKey);
    }
}