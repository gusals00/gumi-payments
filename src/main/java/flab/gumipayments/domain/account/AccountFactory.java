package flab.gumipayments.domain.account;

import flab.gumipayments.domain.signup.SignupCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {
    public Account create(SignupCommand signupCommand, Long signupId) {
        return Account.builder()
                .email(signupCommand.getEmail())
                .name(signupCommand.getName())
                .password(signupCommand.getPassword())
                .signupId(signupId)
                .build();
    }
}
