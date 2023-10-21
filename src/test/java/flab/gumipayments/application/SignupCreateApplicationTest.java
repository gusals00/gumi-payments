package flab.gumipayments.application;

import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupCommand;
import flab.gumipayments.domain.signup.SignupFactory;
import flab.gumipayments.domain.signup.SignupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignupCreateApplicationTest {

    @Mock
    private SignupFactory signupFactory;
    @Mock

    private AcceptRequesterApplication acceptRequestApplication;
    @Mock
    private SignupRepository signupRepository;
    @Mock KeyGeneratorApplication keyGeneratorApplication;

    @InjectMocks
    private SignupCreateApplication signupCreateApplication;

    private SignupCommand signupCommand;
    private Signup signup;

//    @BeforeEach
//    void setup() {
//        signupCommand = new SignupCommand("love@naver.com", "2345", "kim");
//        signup = new Signup(signupCommand.getEmail(), signupCommand.getPassword(), signupCommand.getName());
//    }
//
//    @Test
//    void signup() {
//        String acceptKey = "123";
//        when(signupFactory.create(signupCommand)).thenReturn(signup);
//        when(signupRepository.save(signup)).thenReturn(signup);
//        when(signupRepository.existsByEmail(any())).thenReturn(false);
//        when(keyGeneratorApplication.generateSignupKey()).thenReturn(acceptKey);
//        doNothing().when(acceptRequestApplication).requestSignupAccept(any(),any());
//
//        signupCreateApplication.signup(signupCommand);
//
//        verify(signupRepository).existsByEmail(signup.getEmail());
//        verify(signupFactory).create(signupCommand);
//        verify(acceptRequestApplication).requestSignupAccept(signup.getEmail(), acceptKey);
//        verify(signupRepository).save(signup);
//    }
//
//    // TODO RuntimeException -> 적절한 예외로 변경
//    @Test
//    @DisplayName("중복 가입 요청이 존재하는 경우")
//    void signupReject() {
//        when(signupRepository.existsByEmail(any())).thenReturn(true);
//
//        assertThatThrownBy(() -> signupCreateApplication.signup(signupCommand))
//                .isInstanceOf(RuntimeException.class);
//    }
}