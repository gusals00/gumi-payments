package flab.gumipayments.domain.signup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class SignupFactoryTest {

    private MockedStatic<LocalDateTime> localDateMockedStatic;
    private SignupCreateCommand.SignupCreateCommandBuilder signupCreateCommandBuilder;
    private SignupFactory signupFactory;
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
        String signupKey = "1234";

        Signup signup = signupFactory.create(signupCreateCommand(signupKey,expireDate, email));

        assertThat(signup.getEmail()).isEqualTo(email);
        assertThat(signup.getSignupKey()).isEqualTo(signupKey);
        assertThat(signup.getExpireDate()).isEqualTo(expireDate);
    }

    private SignupCreateCommand signupCreateCommand(String signupKey, LocalDateTime expireDate, String email){
        return signupCreateCommandBuilder
                .signupKey(signupKey)
                .expireDate(expireDate)
                .email(email)
                .build();
    }
}