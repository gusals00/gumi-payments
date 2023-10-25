package flab.gumipayments.application;

import flab.gumipayments.domain.KeyFactory;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupRepository;
import flab.gumipayments.domain.signup.SignupStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignupAcceptApplicationTest {

    @InjectMocks
    SignupAcceptApplication signupAcceptApplication;
    @Mock
    SignupRepository signupRepository;

    private Signup signup;
    private AcceptCommand acceptCommand;

    @BeforeEach
    void setup() {
        String email = "love4@naver.com";
        String signupKey= KeyFactory.generateSignupKey();
        signup = Signup.builder().signupKey(signupKey)
                .email(email)
                .expireDate(LocalDateTime.now().plusDays(1))
                .build();
        acceptCommand =  new AcceptCommand(signup.getSignupKey(), signup.getEmail());

    }
    @Test
    @DisplayName("가입 인증 성공")
    void signupAccept() {
        when(signupRepository.findBySignupKeyAndEmail(acceptCommand.getSignupKey(), acceptCommand.getEmail()))
                .thenReturn(Optional.of(signup));

        signupAcceptApplication.accept(acceptCommand);

        verify(signupRepository).findBySignupKeyAndEmail(acceptCommand.getSignupKey(), acceptCommand.getEmail());
        assertThat(signup.getStatus()).isEqualTo(SignupStatus.ACCEPT);
    }

    @Test
    @DisplayName("timeout된 인증키로 인증 시도")
    void useTimeoutSignupKeyToAccept() {
        signup.timeout();

        when(signupRepository.findBySignupKeyAndEmail(signup.getSignupKey(), signup.getEmail()))
                .thenReturn(Optional.of(signup));

        assertThatThrownBy(()->signupAcceptApplication.accept(acceptCommand))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("올바르지 않은 가입 요청 status 변경입니다.");
    }

    @Test
    @DisplayName("해당 signupKey와 email을 가지는 signup이 없는 경우")
    void doNotHaveSignupWithSignupKeyAndEmail() {
        String email = "love4@naver.com";
        String signupKey= KeyFactory.generateSignupKey();
        when(signupRepository.findBySignupKeyAndEmail(signupKey, email))
                .thenReturn(Optional.empty());

        assertThatThrownBy(()->signupAcceptApplication.accept(new AcceptCommand(signupKey,email)))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("signup이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("timeout")
    void timeout() {
        signupAcceptApplication.timeout();

        verify(signupRepository).updateAllTimeoutSignup(any(), any());
    }

}