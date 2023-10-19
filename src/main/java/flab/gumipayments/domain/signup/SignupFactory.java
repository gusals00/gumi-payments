package flab.gumipayments.domain.signup;

import org.springframework.stereotype.Component;

@Component
public class SignupFactory {

    public Signup create(SignupCommand signupCommand) {
        return Signup.builder()
                .email(signupCommand.getEmail())
                .name(signupCommand.getName())
                .password(signupCommand.getPassword())
                .build();
    }
}
