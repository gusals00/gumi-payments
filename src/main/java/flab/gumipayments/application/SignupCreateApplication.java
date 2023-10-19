package flab.gumipayments.application;

import flab.gumipayments.domain.signup.SignupCommand;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupFactory;
import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupCreateApplication {

    private final SignupFactory signupFactory;
    private final AcceptRequesterApplication acceptRequestApplication;
    private final SignupRepository signupRepository;

    @Transactional
    public Signup signup(SignupCommand signupCommand) {
        // 가입 요청 생성
        if(isReject(signupCommand)) {
            throw new RuntimeException(); // TODO 예외 처리 방식 정하기
        }
        Signup signup = signupFactory.create(signupCommand);

        // TODO 인증 키 생성을 어디서 할지 정하기

        // 인증 요청
        acceptRequestApplication.requestSignupAccept();

        // 가입요청 저장
        signupRepository.save(signup);

        return signup;
    }

    private boolean isReject(SignupCommand signupCommand) {
        // 중복체크

        return false;
    }
}
