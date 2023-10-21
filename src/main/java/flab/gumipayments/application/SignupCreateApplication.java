package flab.gumipayments.application;

import flab.gumipayments.domain.KeyFactory;
import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.signup.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupCreateApplication {

    private final SignupFactory signupFactory;
    private final AcceptRequesterApplication acceptRequestApplication;
    private final SignupRepository signupRepository;
    private final AccountCreateManagerApplication accountCreateManagerApplication;
    private final AccountRepository accountRepository;
    private final SignupAcceptRepository signupAcceptRepository;

    private static final int KEY_EXPIRE_DAYS = 7;

    @Transactional
    public Signup signup(SignupCommand signupCommand) {
        // 중복체크
        if(isReject(signupCommand)) {
            throw new RuntimeException(); // TODO 예외 처리 방식 정하기
        }

        // 가입인증키 생성
        String signupKey = KeyFactory.createSignupKey();

        // 가입 생성
        Signup signup = signupFactory.create(signupKey);
        // 가입 저장
        signupRepository.save(signup);

        // 인증 요청
        acceptRequestApplication.requestSignupAccept(signupCommand.getEmail(), signupKey);

        // 가입인증 저장
        signupAcceptRepository.save(createSignupAccept(signupKey, KEY_EXPIRE_DAYS));

        // 계정 생성
        Account account = accountCreateManagerApplication.create(signupCommand, signup.getId());
        // 계정 저장
        accountRepository.save(account);

        return signup;
    }

    private boolean isReject(SignupCommand signupCommand) {
        return accountRepository.existsByEmail(signupCommand.getEmail());
    }

    private SignupAccept createSignupAccept(String signupKey, int expireDays) {
        return SignupAccept.builder()
                .key(signupKey)
                .expireDate(LocalDateTime.now().plusDays(expireDays))
                .build();
    }
}
