package flab.gumipayments.domain;

import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupCreateCommand;
import flab.gumipayments.domain.signup.SignupFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.signup.SignupCreateCommand.SignupCreateCommandBuilder;
import static org.assertj.core.api.Assertions.assertThat;

class SignupFactoryTest {

    MockedStatic<LocalDateTime> localDateMockedStatic;
    SignupCreateCommandBuilder signupCreateCommandBuilder;
    SignupFactory signupFactory;

    @BeforeEach
    void setup() {
        freezeLocalDateTimeNow();

        signupCreateCommandBuilder = SignupCreateCommand.builder();
        signupFactory = new SignupFactory();
    }
    private void freezeLocalDateTimeNow() {
        LocalDateTime date = LocalDateTime.of(2023, 10, 23, 0, 0);
        localDateMockedStatic = Mockito.mockStatic(LocalDateTime.class);
        localDateMockedStatic.when(LocalDateTime::now).thenReturn(date);
    }
    @AfterEach
    void close() {
        localDateMockedStatic.close();
    }

    @Test
    @DisplayName("성공: 가입 요청 생성을 성공한다.")
    void create() {
        String email = "love@naver.com";
        LocalDateTime expireDate  = LocalDateTime.now().plusDays(1);

        Signup signup = signupFactory.create(signupCreateCommand(expireDate, email));

        assertThat(signup.getEmail()).isEqualTo(email);
        assertThat(signup.getExpireDate()).isEqualTo(expireDate);
    }

    private SignupCreateCommand signupCreateCommand(LocalDateTime expireDate, String email){
        return signupCreateCommandBuilder
                .expireDate(expireDate)
                .email(email)
                .build();
    }
}