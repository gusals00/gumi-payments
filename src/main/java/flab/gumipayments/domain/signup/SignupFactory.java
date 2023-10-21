package flab.gumipayments.domain.signup;

import org.springframework.stereotype.Component;

@Component
public class SignupFactory {

    public Signup create(String signupKey) {
        return Signup.builder()
                .signupKey(signupKey)
                .build();
    }
}
