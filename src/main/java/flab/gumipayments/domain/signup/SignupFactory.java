package flab.gumipayments.domain.signup;

import flab.gumipayments.application.Expire;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static flab.gumipayments.application.Expire.*;

@Component
@RequiredArgsConstructor
public class SignupFactory {

    public Signup create(SignupCommand signupCommand, String signupKey) {
        LocalDateTime expireDate = LocalDateTime.now().plusDays(SIGNUP_KEY_EXPIRE_DAYS)
                .withHour(SIGNUP_KEY_EXPIRE_HOURS)
                .withMinute(SIGNUP_KEY_EXPIRE_MINUTES)
                .withSecond(0)
                .withNano(0);

        return Signup.builder()
                .expireDate(expireDate)
                .email(signupCommand.getEmail())
                .signupKey(signupKey)
                .build();
    }
}
