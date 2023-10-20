package flab.gumipayments.domain.signup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SignupTest {

    private Signup signup;

    @BeforeEach
    void setup() {
        signup = Signup.builder()
                .email("love@naver.com")
                .password("1234")
                .name("민대희")
                .build();
    }

    @Test
    void changeAccept() {
        signup.accept();

        statusCheck(signup.getStatus(),SignupStatus.ACCEPT);
    }

    @Test
    void changeTimeout() {
        signup.timeout();

        statusCheck(signup.getStatus(),SignupStatus.TIMEOUT);
    }

    @Test
    void initStatus() {
        statusCheck(signup.getStatus(),SignupStatus.SIGNUP_REQUEST);
    }

    private void statusCheck(SignupStatus actual,SignupStatus expected) {
        assertThat(actual).isEqualTo(expected);

    }
}