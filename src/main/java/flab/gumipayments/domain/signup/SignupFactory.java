package flab.gumipayments.domain.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SignupFactory {

    public Signup create(SignupCreateCommand signupCommand) {
        return Signup.builder()
                .expireDate(signupCommand.getExpireDate())
                .email(signupCommand.getEmail())
                .signupKey(generateSignupKey())
                .build();
    }

    public static String generateSignupKey() {
        return UUID.randomUUID().toString();
    }

}
