package flab.gumipayments.domain.account;

import flab.gumipayments.domain.signup.Signup;

public class AccountFactory {
    public Account create(Signup signup) {
        return new Account();
    }
}
