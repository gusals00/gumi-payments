package flab.gumipayments.application;

import flab.gumipayments.domain.KeyFactory;
import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.signup.SignupAcceptRepository;
import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReAcceptRequesterApplication {

    private final SignupRepository signupRepository;
    private final AcceptRequesterApplication acceptRequesterApplication;
    private final AccountRepository accountRepository;
    private final SignupAcceptRepository signupAcceptRepository;

    // 재인증 요청
    public void reAcceptRequest(Long signupId) {
        // 가입이 존재하지 않는 경우
        if(!signupRepository.existsById(signupId)) {
            throw new RuntimeException(); // TODO 예외 처리 방식 정하기
        }

        // 계정 찾기
        Account account = accountRepository.findBySignupId(signupId);

        // 키 생성
        String signupKey = KeyFactory.createSignupKey();

        // 키 중복 확인
        if(signupAcceptRepository.existsById(signupKey)) {
            throw new RuntimeException();
        }

        // 인증 요청
        acceptRequesterApplication.requestSignupAccept(account.getEmail(), signupKey);
    }
}
