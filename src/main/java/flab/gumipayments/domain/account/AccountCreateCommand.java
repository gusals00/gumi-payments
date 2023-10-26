package flab.gumipayments.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountCommand {
    private String password;
    private String name;
}
