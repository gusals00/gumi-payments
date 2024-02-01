package flab.gumipayments.domain.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AccountCreateCommand {
    private String password;
    private String name;
}
