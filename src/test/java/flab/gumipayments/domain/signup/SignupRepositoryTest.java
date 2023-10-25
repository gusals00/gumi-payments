package flab.gumipayments.domain.signup;

import flab.gumipayments.domain.KeyFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static flab.gumipayments.domain.signup.SignupStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class SignupRepositoryTest {

    @Autowired
    private SignupRepository signupRepository;
    private Signup signup;

    @BeforeEach
    void setup() {
        signup = Signup.builder()
                .email("love47024702@naver.com")
                .signupKey(KeyFactory.generateSignupKey())
                .expireDate(LocalDateTime.now().plusDays(7))
                .build();
    }

    @Test
    @DisplayName("만료된 가입요청 timeout")
    void updateTimeoutSignup() {
        Signup timeoutSignup = getTimeoutSignup("asdas22@naver.com");
        Signup timeoutSignup2 = getTimeoutSignup("asdas222@naver.com");
        signupRepository.saveAll(List.of(signup, timeoutSignup, timeoutSignup2));

        int timeoutSignupCount = signupRepository.updateAllTimeoutSignup(LocalDateTime.now(), SIGNUP_REQUEST);
        timeoutSignup = signupRepository.findByEmail("asdas22@naver.com")
                .orElseThrow(() -> new NoSuchElementException("signup이 존재하지 않습니다."));
        timeoutSignup2 = signupRepository.findByEmail("asdas222@naver.com")
                .orElseThrow(() -> new NoSuchElementException("signup이 존재하지 않습니다."));

        assertThat(timeoutSignupCount).isEqualTo(2);
        assertThat(timeoutSignup.getStatus()).isEqualTo(TIMEOUT);
        assertThat(timeoutSignup2.getStatus()).isEqualTo(TIMEOUT);
        assertThat(signup.getStatus()).isEqualTo(SIGNUP_REQUEST);
    }

    private Signup getTimeoutSignup(String email) {
        return Signup.builder()
                .expireDate(LocalDateTime.now().minusMonths(10))
                .signupKey(KeyFactory.generateSignupKey())
                .email(email)
                .build();
    }

}