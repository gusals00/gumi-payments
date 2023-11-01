package flab.gumipayments.domain.signup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.signup.SignupStatus.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SignupTest {

    private Signup signup;
    private MockedStatic<LocalDateTime> localDateMockedStatic;

    @BeforeEach
    void setup() {
        setNowLocalDateTime(2023,10,23,1,0);

        signup = Signup.builder()
                .email("love@naver.com")
                .signupKey("1234")
                .expireDate(LocalDateTime.now())
                .build();
    }

    private void setNowLocalDateTime(int year,int month,int day,int hour,int minute) {
        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute);
        localDateMockedStatic = Mockito.mockStatic(LocalDateTime.class);
        localDateMockedStatic.when(LocalDateTime::now).thenReturn(date);
    }

    @AfterEach
    void close() {
        localDateMockedStatic.close();
    }

    @Test
    @DisplayName("accept status로 변경")
    void changeToAccept() {
        signup.accept();
        statusCheck(signup.getStatus(), ACCEPT);
    }


    @Test
    @DisplayName("초기 가입 상태")
    void initStatus() {
        statusCheck(signup.getStatus(), SIGNUP_REQUEST);
    }

    @Test
    @DisplayName("accept 에서 account created로 변경")
    void changeToAcceptCreated() {
        signup.accept();
        signup.accountCreated();
        statusCheck(signup.getStatus(), ACCOUNT_CREATED);
    }

    private void statusCheck(SignupStatus actual,SignupStatus expected) {
        assertThat(actual).isEqualTo(expected);
    }

}