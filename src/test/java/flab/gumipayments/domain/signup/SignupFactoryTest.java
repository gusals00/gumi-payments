package flab.gumipayments.domain.signup;

import flab.gumipayments.domain.KeyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static flab.gumipayments.application.Expire.*;
import static flab.gumipayments.domain.KeyFactory.*;
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
        LocalDateTime expireDate = createExpireDate(SIGNUP_KEY_EXPIRE_DAYS, SIGNUP_KEY_EXPIRE_HOURS, SIGNUP_KEY_EXPIRE_MINUTES);
        SignupCommand signupCommand = new SignupCommand("love@naver.com",expireDate, generateSignupKey());
        SignupFactory signupFactory = new SignupFactory();

        Signup signup = signupFactory.create(signupCommand);

        assertThat(signup.getEmail()).isEqualTo(signupCommand.getEmail());
        assertThat(signup.getSignupKey()).isEqualTo(signupCommand.getSignupKey());
        assertThat(signup.getExpireDate()).isEqualTo(signupCommand.getExpireDate());
    }

    private LocalDateTime createExpireDate(int days, int hours, int minutes) {
        return LocalDateTime.now()
                .plusDays(days)
                .withHour(hours)
                .withMinute(minutes);
    }
}