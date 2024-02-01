package flab.gumipayments.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.CONTRACT_REQUEST;

    private Long accountId;

    @Builder
    public Contract(ContractStatus status,Long accountId) {
        if (status != null) {
            this.status = status;
        }
        this.accountId = accountId;
    }
}
