package flab.gumipayments.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

@Getter
public class RequestCommand {

    private String orderId;
    private String orderName;
    private Long amount;
    private CardCompany cardCompany;
    private Customer customer;
    private Long contractId;
    private String successUrl;
    private String failUrl;

    @Builder
    public RequestCommand(String orderId, String orderName, CardCompany cardCompany, Long amount, Customer customer, Long contractId, String successUrl, String failUrl) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.cardCompany = cardCompany;
        this.amount = amount;
        this.customer = customer;
        this.contractId = contractId;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }
}
