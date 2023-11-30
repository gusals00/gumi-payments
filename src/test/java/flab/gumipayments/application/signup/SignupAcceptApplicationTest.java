package flab.gumipayments.application.signup;

import flab.gumipayments.application.NotFoundException;
import flab.gumipayments.application.signup.AcceptCommand;
import flab.gumipayments.application.signup.SignupAcceptApplication;
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

import static flab.gumipayments.application.signup.AcceptCommand.*;
import static flab.gumipayments.domain.signup.Signup.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignupAcceptApplicationTest {

    @InjectMocks
    private SignupAcceptApplication sut;
    @Mock
    private SignupRepository signupRepository;

    private AcceptCommandBuilder acceptCommandBuilder;
    private SignupBuilder signupBuilder;

    private Signup signup;
    private AcceptCommand acceptCommand;

    @BeforeEach
    void setup() {
        acceptCommandBuilder = AcceptCommand.builder();
        signupBuilder = Signup.builder();
        signup = signupBuilder
                .signupKey("1234").expireDate(LocalDateTime.now().plusDays(1)).build();

    }

    @Test
    @DisplayName("예외: 가입 요청이 존재하지 않으면 계정 생성이 실패한다.")
    void doNotHaveSignupWithSignupKeyAndEmail() {
        acceptCommand = acceptCommandBuilder
                .signupKey("1234").build();
        when(signupRepository.findBySignupKey(acceptCommand.getSignupKey()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(()-> sut.accept(acceptCommand))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("signup이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("성공: 가입 요청의 인증이 성공한다.")
    void signupAccept() {
        acceptCommand = acceptCommandBuilder
                .signupKey("1234").build();
        when(signupRepository.findBySignupKey("1234"))
                .thenReturn(Optional.of(signup));

        sut.accept(acceptCommand);

        assertThat(signup.getStatus()).isEqualTo(SignupStatus.ACCEPT);
    }
}