package flab.gumipayments.application;

import lombok.Getter;

@Getter
public class PaymentAcceptException extends RuntimeException{

    private String orderId;
    private String failUrl;

    public PaymentAcceptException(String message, String orderId,String failUrl) {
        super(message);
        this.orderId = orderId;
        this.failUrl = failUrl;
    }
}
