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

    private SignupCreateCommand signupCreateCommand;
    private Signup signup;

    private static final int EXPIRE_DAYS=7;
    private static final int EXPIRE_HOURS=7;
    private static final int EXPIRE_MINUTES=0;
    @BeforeEach
    void setup() {
        LocalDateTime expireDate = LocalDateTime.now()
                .plusDays(EXPIRE_DAYS)
                .plusDays(EXPIRE_HOURS)
                .plusMinutes(EXPIRE_MINUTES);
        signupCreateCommand = new SignupCreateCommand("love47024702@naver.com",expireDate, generateSignupKey());
        signup = Signup.builder()
                .signupKey(signupCreateCommand.getSignupKey())
                .expireDate(signupCreateCommand.getExpireDate())
                .email(signupCreateCommand.getEmail())
                .build();
    }

    @Test
    @DisplayName("가입 요청 성공(가입 요청한 적이 없는 이메일)")
    void haveNeverSignedUpEmail() {
        when(signupRepository.findByEmail(signup.getEmail())).thenReturn(Optional.empty());
        when(signupFactory.create(signupCreateCommand)).thenReturn(signup);
        doNothing().when(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey(),signupCreateCommand.getExpireDate());
        when(signupRepository.save(signup)).thenReturn(signup);

        signupCreateApplication.signup(signupCreateCommand);

        verify(signupRepository,times(2)).findByEmail(signup.getEmail());
        verify(signupFactory).create(signupCreateCommand);
        verify(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey(),signupCreateCommand.getExpireDate());
        verify(signupRepository).save(signup);
    }

    @Test
    @DisplayName("가입 요청 성공(가입 요청한 적이 있지만, 해당 이메일로 계정이 생성된 적이 없는 경우)")
    void haveSignedUpButNotCreatedAccount() {
        when(signupRepository.findByEmail(signup.getEmail())).thenReturn(Optional.ofNullable(signup));
        when(signupFactory.create(signupCreateCommand)).thenReturn(signup);
        doNothing().when(signupRepository).delete(signup);
        doNothing().when(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey(),signupCreateCommand.getExpireDate());
        when(signupRepository.save(signup)).thenReturn(signup);

        signupCreateApplication.signup(signupCreateCommand);

        verify(signupRepository,times(2)).findByEmail(signup.getEmail());
        verify(signupRepository).delete(signup);
        verify(signupFactory).create(signupCreateCommand);
        verify(acceptRequestApplication).requestSignupAccept(signup.getEmail(), signup.getSignupKey(),signupCreateCommand.getExpireDate());
        verify(signupRepository).save(signup);
    }

    @Test
    @DisplayName("가입 요청으로 계정 생성한 적이 있는 경우")
    void signupAlreadyExistEmail() {
        signup.accept();
        signup.accountCreated();
        when(signupRepository.findByEmail(signup.getEmail())).thenReturn(Optional.ofNullable(signup));

        Assertions.assertThatThrownBy(()-> signupCreateApplication.signup(signupCreateCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이메일로 생성한 계정이 이미 존재합니다.");

    }
}