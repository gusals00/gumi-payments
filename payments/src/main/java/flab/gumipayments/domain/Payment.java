package flab.gumipayments.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    private String paymentKey;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String orderId;

    private String orderName;

    private LocalDateTime requestAt;

    private LocalDateTime approveAt;

    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private Long totalAmount;

    private Long balanceAmount;

    private String lastTransactionKey;

    @Embedded
    private Card card;

    @Enumerated(EnumType.STRING)
    private EasyPayType easyPayType;

    @Embedded
    private Customer customer;

    private Long contractId;

    private String successUrl;

    private String failUrl;

    public void updateDone(PaymentStatus paymentStatus) {
        if(paymentStatus != PaymentStatus.IN_PROGRESS) {
            throw new IllegalStateException("올바르지 않은 결제 status 변경입니다.");
        }
        this.status = paymentStatus;
    }

    public Transaction createTransactionByConfirm(ConfirmCommand confirmCommand) {
        String transactionKey = UUID.randomUUID().toString();
        this.lastTransactionKey = transactionKey;

        return Transaction.builder()
                .amount(confirmCommand.getAmount())
                .transactionKey(transactionKey)
                .transactionAt(LocalDateTime.now())
                .payment(this)
                .refundableAmount(confirmCommand.getAmount())
                .build();
    }

    @Builder
    public Payment(String paymentKey, PaymentStatus status, String orderId, String orderName, LocalDateTime requestAt, LocalDateTime approveAt, LocalDateTime expiredAt, PaymentMethod method, Long totalAmount, Long balanceAmount, String lastTransactionKey, Card card, EasyPayType easyPayType, Customer customer, Long contractId, String successUrl, String failUrl) {
        this.paymentKey = paymentKey;
        this.status = status;
        this.orderId = orderId;
        this.orderName = orderName;
        this.requestAt = requestAt;
        this.approveAt = approveAt;
        this.expiredAt = expiredAt;
        this.method = method;
        this.totalAmount = totalAmount;
        this.balanceAmount = balanceAmount;
        this.lastTransactionKey = lastTransactionKey;
        this.card = card;
        this.easyPayType = easyPayType;
        this.customer = customer;
        this.contractId = contractId;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }
}
