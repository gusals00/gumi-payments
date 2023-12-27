package flab.gumipayments.application.signup;

import flab.gumipayments.application.NotFoundSystemException;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupAcceptApplication {

    private final SignupRepository signupRepository;

    @Transactional
    public Long accept(AcceptCommand acceptCommand) {
        // findBy인증키
        Signup signup = signupRepository.findBySignupKey(acceptCommand.getSignupKey())
                .orElseThrow(() -> new NotFoundSystemException("signup이 존재하지 않습니다."));

        // accept
        signup.accept();

        return signup.getId();
    }
}
