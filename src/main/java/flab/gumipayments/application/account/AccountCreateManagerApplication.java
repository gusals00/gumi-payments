package flab.gumipayments.application.account;

import flab.gumipayments.application.NotFoundSystemException;
import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.account.AccountCreateCommand;
import flab.gumipayments.domain.account.AccountFactory;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCreateManagerApplication {

    private final AccountFactory accountFactory;
    private final AccountRepository accountRepository;
    private final SignupRepository signupRepository;

    @Transactional
    public Account create(AccountCreateCommand accountCreateCommand, Long signupId) {
        // 가입 찾기
        Signup signup = signupRepository.findById(signupId)
                .orElseThrow(() -> new NotFoundSystemException("signup이 존재하지 않습니다."));

        // 계정 생성 상태로 변경
        signup.accountCreated();

        // 계정 생성
        Account account = accountFactory.create(accountCreateCommand, signup.getEmail());

        // 계정 저장
        accountRepository.save(account);

        return account;
    }
}
