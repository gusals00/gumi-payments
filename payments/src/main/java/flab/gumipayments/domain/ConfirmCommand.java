package flab.gumipayments.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ConfirmCommand {

    private String paymentKey;

    private String orderId;

    private Long amount;

    private String idempotencyKey;

    private Long apiKeyId;
}
