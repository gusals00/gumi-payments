package flab.gumipayments.application;

import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupRepository;
import flab.gumipayments.domain.signup.SignupStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
                .orElseThrow(()->new NoSuchElementException("signup이 존재하지 않습니다."));

        if(!signup.getExpireDate().isEqual(acceptCommand.getExpireDate())){
            throw new IllegalArgumentException("만료시간이 올바르지 않습니다.");
        }

        // accept
        signup.accept();

        return signup.getId();
    }

    @Transactional
    public void timeout() {
        // 만료된 가입요청 timeout
        int timeoutSignup = signupRepository.updateAllTimeoutSignup(LocalDateTime.now(), SignupStatus.SIGNUP_REQUEST);

        log.info("timeout 가입요청 개수 = {}", timeoutSignup);
    }
}
