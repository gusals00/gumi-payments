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
        statusCheck(signup.getStatus(),SignupStatus.ACCEPT);
    }

    @Test
    @DisplayName("timeout status로 변경")
    void changeToTimeout() {
        signup.timeout();
        statusCheck(signup.getStatus(),SignupStatus.TIMEOUT);
    }

    @Test
    @DisplayName("초기 가입 상태")
    void initStatus() {
        statusCheck(signup.getStatus(),SignupStatus.SIGNUP_REQUEST);
    }

    @Test
    @DisplayName("status를 accept에서 timeout으로 변경")
    void changeToInvalidTimeout() {
        signup.accept();

        assertThatThrownBy(()->signup.timeout())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("올바르지 않은 가입 요청 status 변경입니다.");
    }

    @Test
    @DisplayName("status를 timeout에서 accept로 변경")
    void changeToInvalidAccept() {
        signup.timeout();

        assertThatThrownBy(()->signup.accept())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("올바르지 않은 가입 요청 status 변경입니다.");
    }

    @Test
    @DisplayName("isAccountCreated = true로 변경")
    void changeIsAccountCreated() {
        // isAccountCreated 변경 전
        assertThat(signup.isAccountCreated()).isFalse();

        signup.accountCreated();

        // isAccountCreated 변경 후
        assertThat(signup.isAccountCreated()).isTrue();

    }

    private void statusCheck(SignupStatus actual,SignupStatus expected) {
        assertThat(actual).isEqualTo(expected);
    }

}