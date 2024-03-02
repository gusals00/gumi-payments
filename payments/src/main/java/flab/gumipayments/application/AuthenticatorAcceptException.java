package flab.gumipayments.application;

import lombok.Getter;

@Getter
public class AuthenticatorAcceptException extends RuntimeException {

    private String orderId;
    private String failUrl;

    public AuthenticatorAcceptException(String message, String orderId,String failUrl) {
        super(message);
        this.orderId = orderId;
        this.failUrl = failUrl;
    }
}
