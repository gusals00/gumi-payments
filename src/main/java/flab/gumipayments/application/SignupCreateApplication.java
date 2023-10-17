package flab.gumipayments.application;

import flab.gumipayments.domain.signup.SignupCommand;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupFactory;
import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupCreateApplication {

    private final SignupFactory signupFactory;
    private final AcceptRequesterApplication acceptRequestApplication;
    private final SignupRepository signupRepository;

    public Signup signup(SignupCommand signupCommand) {
        // 가입 요청 생성
        if(isReject(signupCommand)) {
            throw new RuntimeException();
        }
        Signup signupRequest = signupFactory.create(signupCommand);

        // 인증 요청
        acceptRequestApplication.requestSignupAccept();

        // 가입요청 저장
        signupRepository.save(signupRequest);

        return signupRequest;
    }

    private boolean isReject(SignupCommand signupCommand) {
        // 중복체크

        return false;
    }
}
