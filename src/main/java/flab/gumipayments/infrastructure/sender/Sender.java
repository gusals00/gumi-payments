package flab.gumipayments.infrastructure.sender;

import java.time.LocalDateTime;

public interface Sender {
    void
    sendSignupRequest(String toAddress, String signupKey, LocalDateTime expireDate);
}
