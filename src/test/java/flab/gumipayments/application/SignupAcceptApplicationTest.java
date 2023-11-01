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
        acceptCommand =  new AcceptCommand(signup.getSignupKey());

    }
    @Test
    @DisplayName("가입 인증 성공")
    void signupAccept() {
        when(signupRepository.findBySignupKey(acceptCommand.getSignupKey()))
                .thenReturn(Optional.of(signup));

        signupAcceptApplication.accept(acceptCommand);

        verify(signupRepository).findBySignupKey(acceptCommand.getSignupKey());
        assertThat(signup.getStatus()).isEqualTo(SignupStatus.ACCEPT);
    }



    @Test
    @DisplayName("해당 signupKey을 가지는 signup이 없는 경우")
    void doNotHaveSignupWithSignupKeyAndEmail() {
        when(signupRepository.findBySignupKey(any()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(()->signupAcceptApplication.accept(acceptCommand))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("signup이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("만료 시간이 올바르지 않은 경우")
    void doesNotRightExpiredDate() {
        AcceptCommand invalidCommand = new AcceptCommand(acceptCommand.getSignupKey());
        when(signupRepository.findBySignupKey(signup.getSignupKey()))
               .thenReturn(Optional.of(signup));

        assertThatThrownBy(()->signupAcceptApplication.accept(invalidCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("만료 시간이 지났습니다.");
    }

    @Test
    @DisplayName("timeout")
    void timeout() {
        signupAcceptApplication.timeout();

        verify(signupRepository).updateAllTimeoutSignup(any(), any());
    }

}