package flab.gumipayments.application;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class PaymentRequestCreateCommand {
    private String orderId;
    private String orderName;
    private String customerEmail;
    private String customerName;
    private Long amount;
    private String cardCompany;
    private String successUrl;
    private String failUrl;
    private Long apiKeyId;

    @Builder
    public PaymentRequestCreateCommand(String orderId, String orderName, String customerEmail, String customerName, Long amount, String cardCompany, String successUrl, String failUrl,Long apiKeyId) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.amount = amount;
        this.cardCompany = cardCompany;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
        this.apiKeyId = apiKeyId;
    }
}
