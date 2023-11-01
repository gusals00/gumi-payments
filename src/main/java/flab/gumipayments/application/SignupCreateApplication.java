package flab.gumipayments.application;

import flab.gumipayments.domain.signup.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.gumipayments.domain.signup.SignupStatus.*;

@Service
@RequiredArgsConstructor
public class SignupCreateApplication {

    private final SignupFactory signupFactory;
    private final AcceptRequesterApplication acceptRequestApplication;
    private final SignupRepository signupRepository;

    @Transactional
    public void signup(SignupCreateCommand signupCreateCommand) {

        // 가입 생성
        Signup signup = create(signupCreateCommand);

        // 인증 요청
        acceptRequestApplication.requestSignupAccept(signupCreateCommand.getEmail(), signup.getSignupKey());

        // 가입 저장
        signupRepository.save(signup);
    }


    private Signup create(SignupCreateCommand signupCommand) {

        // 해당 email로 계정이 생성 되었는지 확인
        validateEmail(signupCommand);

        // 가입 요청이 존재하면 삭제
        signupRepository.findByEmail(signupCommand.getEmail())
                .ifPresent(signupRepository::delete);

        // 가입 생성
        return signupFactory.create(signupCommand);
    }

    private void validateEmail(SignupCreateCommand signupCommand) {
        signupRepository.findByEmail(signupCommand.getEmail())
                .ifPresent(signup -> {
                    if (signup.getStatus() == ACCOUNT_CREATED) {
                        throw new IllegalArgumentException("해당 이메일로 생성한 계정이 이미 존재합니다.");
                    }
                });
    }
}
