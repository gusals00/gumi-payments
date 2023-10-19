package flab.gumipayments.domain.account;

import flab.gumipayments.domain.signup.Signup;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {
    public Account create(Signup signup) {
        return Account.builder()
                .signup(signup)
                .email(signup.getEmail())
                .name(signup.getName())
                .password(signup.getPassword())
                .build();
    }
}
