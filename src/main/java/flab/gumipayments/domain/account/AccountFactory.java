package flab.gumipayments.domain.account;

import org.springframework.stereotype.Component;

@Component
public class AccountFactory {

    public Account create(AccountCreateCommand accountCommand, String email) {
        return Account.builder()
                .email(email)
                .name(accountCommand.getName())
                .password(accountCommand.getPassword())
                .build();
    }
}
