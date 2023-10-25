package flab.gumipayments.application;

import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.account.AccountCommand;
import flab.gumipayments.domain.account.AccountFactory;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCreateManagerApplication {

    private final AccountFactory accountFactory;
    private final AccountRepository accountRepository;
    private final SignupRepository signupRepository;

    @Transactional
    public Account create(AccountCommand accountCommand, Long signupId) {
        // 가입 찾기
        Signup signup = signupRepository.findById(signupId)
                .orElseThrow(() -> new NoSuchElementException("signup이 존재하지 않습니다."));

        // 이미 생성된 계정이 있는지 확인
        if(signup.isAccountCreated())
            throw new IllegalArgumentException("해당 이메일로 생성한 계정이 이미 존재합니다.");

        // 계정 생성
        Account account = accountFactory.create(accountCommand, signup.getEmail());

        // 계정 저장
        accountRepository.save(account);

        // 계정 생성 상태로 변경
        signup.accountCreated();

        return account;
    }
}
