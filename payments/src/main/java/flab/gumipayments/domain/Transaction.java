package flab.gumipayments.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
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
}
