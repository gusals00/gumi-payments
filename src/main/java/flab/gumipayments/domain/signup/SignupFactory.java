package flab.gumipayments.domain.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SignupFactory {

    public Signup create(SignupCommand signupCommand, String signupKey ,int expireDay) {
        LocalDateTime expireDate = LocalDateTime.now().plusDays(expireDay);
        return Signup.builder()
                .expireDate(expireDate)
                .email(signupCommand.getEmail())
                .signupKey(signupKey)
                .build();
    }
}
