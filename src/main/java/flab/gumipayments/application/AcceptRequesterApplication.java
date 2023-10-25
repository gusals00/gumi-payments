package flab.gumipayments.application;

import flab.gumipayments.infrastructure.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcceptRequesterApplication {

    private final Sender sender;

    @Async("emailExecutor")
    public void requestSignupAccept(String toAddress, String signupKey) {
        log.info("email 전송");
        sender.sendSignupRequest(toAddress, signupKey);
    }

}
