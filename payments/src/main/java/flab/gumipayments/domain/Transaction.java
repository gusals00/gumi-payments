package flab.gumipayments.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    private String transactionKey;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "payment_key")
    private Payment payment;

    private LocalDateTime transactionAt;

    private Long amount;

    private Long refundableAmount;

    private String cancelReason;

    @Builder
    public Transaction(String transactionKey, Payment payment, LocalDateTime transactionAt, Long amount, Long refundableAmount, String cancelReason) {
        this.transactionKey = transactionKey;
        this.payment = payment;
        this.transactionAt = transactionAt;
        this.amount = amount;
        this.refundableAmount = refundableAmount;
        this.cancelReason = cancelReason;
    }
}
