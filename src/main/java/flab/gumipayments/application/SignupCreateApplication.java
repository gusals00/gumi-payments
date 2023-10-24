package flab.gumipayments.application;

import flab.gumipayments.domain.KeyFactory;
import flab.gumipayments.domain.signup.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class SignupCreateApplication {

    private final SignupFactory signupFactory;
    private final AcceptRequesterApplication acceptRequestApplication;
    private final SignupRepository signupRepository;

    @Transactional
    public void signup(SignupCommand signupCommand) {

        // 가입 생성
        Signup signup = create(signupCommand);

        // 인증 요청
        acceptRequestApplication.requestSignupAccept(signupCommand.getEmail(), signup.getSignupKey());

        // 가입 저장
        signupRepository.save(signup);
    }


    private Signup create(SignupCommand signupCommand){

        // 해당 email로 계정이 생성 되었는지 확인
        validEmail(signupCommand);

        // 인증키 생성
        String signupKey = KeyFactory.generateSignupKey();

        // 가입 요청이 존재하면 삭제
        Optional<Signup> findSignup = signupRepository.findByEmail(signupCommand.getEmail());
        findSignup.ifPresent(signupRepository::delete);

        // 가입 생성
        return signupFactory.create(signupCommand, signupKey, ExpireDays.SIGNUP_KEY_EXPIRE_DAYS);
    }

    private void validEmail(SignupCommand signupCommand) {
        signupRepository.findByEmail(signupCommand.getEmail())
                .ifPresent(signup -> {
                    if (signup.isAccountCreated()) {
                        throw new IllegalArgumentException("해당 이메일로 생성한 계정이 이미 존재합니다.");
                    }
                });
    }
}
