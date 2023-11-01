package flab.gumipayments.domain.signup;

import flab.gumipayments.domain.KeyFactory;
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


    private Signup getTimeoutSignup(String email) {
        return Signup.builder()
                .expireDate(LocalDateTime.now().minusMonths(10))
                .signupKey(KeyFactory.generateSignupKey())
                .email(email)
                .build();
    }

}