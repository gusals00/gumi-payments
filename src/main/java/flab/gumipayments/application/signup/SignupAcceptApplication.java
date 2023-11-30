package flab.gumipayments.application.signup;

import flab.gumipayments.application.NotFoundException;
import flab.gumipayments.application.signup.AcceptCommand;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupRepository;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupAcceptApplication {

    private final SignupRepository signupRepository;

    @Transactional
    public Long accept(AcceptCommand acceptCommand) {
        // findBy인증키
        Signup signup = signupRepository.findBySignupKey(acceptCommand.getSignupKey())
                .orElseThrow(() -> new NotFoundException(SystemErrorCode.NOT_FOUND, "signup이 존재하지 않습니다."));

        // accept
        signup.accept();

        return signup.getId();
    }
}
