package flab.gumipayments.infrastructure.sender;

import java.time.LocalDateTime;

public interface Sender {
    void sendSignupRequest(String email, String signupKey);

    void sendApiKeyRenewRequest(String email, LocalDateTime expireDate);
}
