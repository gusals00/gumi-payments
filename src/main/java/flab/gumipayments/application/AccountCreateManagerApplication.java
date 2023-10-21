package flab.gumipayments.application;

import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.account.AccountFactory;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.signup.SignupCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCreateManagerApplication {

    private final AccountFactory accountFactory;
    private final AccountRepository accountRepository;

    public Account create(SignupCommand signupCommand, Long signupId) {
        // 계정 생성
        Account account = accountFactory.create(signupCommand, signupId);

        // 계정 저장
        accountRepository.save(account);

        return account;
    }
}
