package flab.gumipayments.domain.signup;

import flab.gumipayments.application.Expire;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static flab.gumipayments.application.Expire.*;

@Component
@RequiredArgsConstructor
public class SignupFactory {

    public Signup create(SignupCommand signupCommand) {
        return Signup.builder()
                .expireDate(signupCommand.getExpireDate())
                .email(signupCommand.getEmail())
                .signupKey(signupCommand.getSignupKey())
                .build();
    }
}
