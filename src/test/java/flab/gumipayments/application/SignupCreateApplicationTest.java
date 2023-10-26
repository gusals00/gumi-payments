package flab.gumipayments.application;

import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupCreateCommand;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignupCreateApplicationTest {

    @Mock
    private SignupFactory signupFactory;
    @Mock
    private AcceptRequesterApplication acceptRequestApplication;
    @Mock
    private SignupRepository signupRepository;
    @InjectMocks
    private SignupCreateApplication signupCreateApplication;

    private SignupCreateCommand signupCommand;
    private Signup signup;

    private static final int EXPIRE_DAYS=7;
    private static final int EXPIRE_HOURS=7;
    private static final int EXPIRE_MINUTES=0;
    @BeforeEach
    void setup() {
        LocalDateTime expireDate = LocalDateTime.now()
                .plusDays(EXPIRE_DAYS)
                .withHour(EXPIRE_HOURS)
                .withMinute(EXPIRE_MINUTES);
        signupCommand = new SignupCreateCommand("love47024702@naver.com",expireDate, generateSignupKey());
        signup = Signup.builder()
                .signupKey(signupCommand.getSignupKey())
                .expireDate(signupCommand.getExpireDate())
                .email(signupCommand.getEmail())
                .build();
    }
    @Test
    @DisplayName("가입 요청 성공(가입 요청한 적이 없는 이메일)")
    void haveNeverSignedUpEmail() {
        when(signupRepository.findByEmail(signup.getEmail())).thenReturn(Optional.empty());
        when(signupFactory.create(signupCommand)).thenReturn(signup);
        doNothing().when(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey());
        when(signupRepository.save(signup)).thenReturn(signup);

        signupCreateApplication.signup(signupCommand);

        verify(signupRepository,times(2)).findByEmail(signup.getEmail());
        verify(signupFactory).create(signupCommand);
        verify(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey());
        verify(signupRepository).save(signup);
    }

    @Test
    @DisplayName("가입 요청 성공(가입 요청한 적이 있지만, 해당 이메일로 계정이 생성된 적이 없는 경우)")
    void haveSignedUpButNotCreatedAccount() {
        when(signupRepository.findByEmail(signup.getEmail())).thenReturn(Optional.ofNullable(signup));
        when(signupFactory.create(signupCommand)).thenReturn(signup);
        doNothing().when(signupRepository).delete(signup);
        doNothing().when(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey());
        when(signupRepository.save(signup)).thenReturn(signup);

        signupCreateApplication.signup(signupCommand);

        verify(signupRepository,times(2)).findByEmail(signup.getEmail());
        verify(signupRepository).delete(signup);
        verify(signupFactory).create(signupCommand);
        verify(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey());
        verify(signupRepository).save(signup);
    }

    @Test
    @DisplayName("가입 요청으로 계정 생성한 적이 있는 경우")
    void signupAlreadyExistEmail() {
        signup.accountCreated();
        when(signupRepository.findByEmail(signup.getEmail())).thenReturn(Optional.ofNullable(signup));

        Assertions.assertThatThrownBy(()-> signupCreateApplication.signup(signupCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이메일로 생성한 계정이 이미 존재합니다.");

    }
}