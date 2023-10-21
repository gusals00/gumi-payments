package flab.gumipayments.application;

import flab.gumipayments.infrastructure.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AcceptRequesterApplication {

    private final Sender sender;

    public void requestSignupAccept(String toAddress, String signupKey) {
        sender.sendSignupRequest(toAddress, signupKey);
    }

    // branch test
}
