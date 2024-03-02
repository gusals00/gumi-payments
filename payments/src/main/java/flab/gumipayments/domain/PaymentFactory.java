package flab.gumipayments.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentFactory {
    public Payment create(RequestCommand command) {
        String paymentKey = UUID.randomUUID().toString();
        return Payment.builder()
                .paymentKey(paymentKey)
                .status(PaymentStatus.READY)
                .orderId(command.getOrderId())
                .orderName(command.getOrderName())
                .method(PaymentMethod.CARD) // 이 부분도 애매함 -> 카카오 페이 같은 거 들어오면 달라져야 함
                .totalAmount(command.getAmount())
                .balanceAmount(command.getAmount())
                .customer(command.getCustomer())
                .contractId(command.getContractId())
                .successUrl(command.getSuccessUrl())
                .failUrl(command.getFailUrl())
                .build();

        // card도 이렇게 넣으면 안되고, 처음에 (어떤 결제 종류, 인증 처리사)인지만을 받고 인증이 되면 카드번호 등 저장해야 할듯
    }
}
