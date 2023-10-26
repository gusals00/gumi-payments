package flab.gumipayments.domain.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupFactory {

    public Signup create(SignupCreateCommand signupCommand) {
        return Signup.builder()
                .expireDate(signupCommand.getExpireDate())
                .email(signupCommand.getEmail())
                .signupKey(signupCommand.getSignupKey())
                .build();
    }
}
