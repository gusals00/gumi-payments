package flab.gumipayments.application.signup;

import flab.gumipayments.application.DuplicateException;
import flab.gumipayments.application.signup.AcceptRequesterApplication;
import flab.gumipayments.application.signup.SignupCreateApplication;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupCreateCommand;
import flab.gumipayments.domain.signup.SignupCreateCommand.SignupCreateCommandBuilder;
import flab.gumipayments.domain.signup.SignupFactory;
import flab.gumipayments.domain.signup.SignupRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static flab.gumipayments.domain.KeyFactory.*;
import static flab.gumipayments.domain.signup.SignupCreateCommand.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignupCreateApplicationTest {

    @Mock
    private SignupFactory signupFactory;
    @Mock
    private SignupRepository signupRepository;
    @InjectMocks
    private SignupCreateApplication sut;
    @Mock
    private AcceptRequesterApplication acceptRequestApplication;

    private SignupCreateCommand signupCreateCommand;
    private SignupCreateCommandBuilder signupCreateCommandBuilder;
    private Signup signup;

    private static final int EXPIRE_MINUTES=5;

    @BeforeEach
    void setup() {
        LocalDateTime expireDate = LocalDateTime.now()
                .plusMinutes(EXPIRE_MINUTES);

        signupCreateCommand = new SignupCreateCommand("email@naver.com", expireDate, generateSignupKey());
        signupCreateCommandBuilder = builder()
                .expireDate(expireDate)
                .signupKey(signupCreateCommand.getSignupKey());

        signup = Signup.builder()
                .signupKey(signupCreateCommand.getSignupKey())
                .expireDate(signupCreateCommand.getExpireDate())
                .email(signupCreateCommand.getEmail())
                .build();
    }

    @Test
    @DisplayName("성공: 가입 요청 시 가입 요청을 성공한다.")
    void signupSuccess() {
        doNothing().when(acceptRequestApplication).requestSignupAccept(any(), any());
        when(signupFactory.create(signupCreateCommand)).thenReturn(signup);

        sut.signup(signupCreateCommand);

        verify(signupFactory).create(signupCreateCommand);
        verify(signupRepository).save(signup);
    }

    @Test
    @DisplayName("예외: 같은 이메일로 생성한 계정이 존재하면 가입 요청이 실패한다.")
    void signupAlreadyExistEmail() {
        setAccountCreated();
        when(signupRepository.findByEmail(signup.getEmail())).thenReturn(Optional.ofNullable(signup));
        SignupCreateCommand createCommand = signupCreateCommandBuilder.email(signup.getEmail()).build();

        Assertions.assertThatThrownBy(()-> sut.signup(createCommand))
                .isInstanceOf(DuplicateException.class)
                .hasMessage("해당 이메일로 생성한 계정이 이미 존재합니다.");
    }

    private void setAccountCreated() {
        signup.accept();
        signup.accountCreated();
    }
}