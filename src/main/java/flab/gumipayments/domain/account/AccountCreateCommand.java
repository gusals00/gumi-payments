package flab.gumipayments.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountCreateCommand {
    private String password;
    private String name;
}
