package flab.gumipayments.domain.contract;

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
    @Column(name = "contract_id")
    private Long id;


    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.CONTRACT_REQUEST;

    private Long accountId;

    @Builder
    public Contract(Long id,ContractStatus status,Long accountId) {
        this.id = id;
        this.status = status;
        this.accountId = accountId;
    }
}
