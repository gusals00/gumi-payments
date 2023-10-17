package flab.gumipayments.application;

import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.account.AccountFactory;
import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignupAcceptApplication {

    private final SignupRepository signupRepository;
    private final AccountFactory accountFactory;

    public void accept(String id) {
        // findBy인증ID
        Signup findSignupRequest = signupRepository.findByAcceptId(id);

        // accept
        findSignupRequest.accept();

        // 계정 생성 요청
        accountFactory.create(findSignupRequest);
    }

    public void timeout() {
        // find 타임아웃
        List<Signup> timeoutRequests = signupRepository.findTimeoutRequests();

        // timeout 호출
        timeoutRequests.forEach(Signup::timeout);
    }
}
