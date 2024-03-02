package flab.gumipayments.application;

import org.springframework.stereotype.Service;

@Service
public class PaymentAuthenticator {
    public String authenticateUrl(String callbackUrl) {
        return "결제창 url";
    }
}
