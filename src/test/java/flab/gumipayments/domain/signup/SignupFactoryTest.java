package flab.gumipayments.domain.signup;

import flab.gumipayments.domain.KeyFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SignupFactoryTest {

    private MockedStatic<LocalDateTime> localDateMockedStatic;

    @BeforeEach
    void setup() {
        LocalDateTime date = LocalDateTime.of(2023, 10, 23, 1, 0);
        localDateMockedStatic = Mockito.mockStatic(LocalDateTime.class);
        localDateMockedStatic.when(LocalDateTime::now).thenReturn(date);
    }

    @AfterEach
    void close() {
        localDateMockedStatic.close();
    }

    @Test
    @DisplayName("가입 요청 생성")
    void create() {
        SignupCommand signupCommand = new SignupCommand("love@naver.com");
        SignupFactory signupFactory = new SignupFactory();
        String signupKey = KeyFactory.generateSignupKey();
        LocalDateTime now = LocalDateTime.now();
        int days = 1;

        Signup signup = signupFactory.create(signupCommand, signupKey,days);

        assertThat(signup.getEmail()).isEqualTo(signupCommand.getEmail());
        assertThat(signup.getSignupKey()).isEqualTo(signupKey);
        assertThat(signup.getExpireDate()).isEqualTo(now.plusDays(days));
    }
}