package flab.gumipayments.application;

import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReAcceptRequesterApplication {

    private final SignupRepository signupRepository;
    private final AcceptRequesterApplication acceptRequesterApplication;

    // 재인증 요청
    public void reAcceptRequest(Long id) {
        // 가입요청이 존재하지 않는 경우
        if(!signupRepository.existsById(id)) {
            throw new RuntimeException();
        }

        // 인증 요청
        acceptRequesterApplication.requestSignupAccept();
    }
}
