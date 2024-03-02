package flab.gumipayments.application;

import flab.gumipayments.domain.*;
import flab.gumipayments.domain.contract.Contract;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestCommandCreatorApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final ContractRepository contractRepository;

    public RequestCommand getRequestCommand(PaymentRequestCreateCommand createCommand) {

        ApiKey apiKey = apiKeyRepository.findById(createCommand.getApiKeyId()).orElseThrow(() -> new NotFoundSystemException("존재하지 않는 api 키 입니다."));
        Contract contract = contractRepository.findByAccountId(apiKey.getAccountId()).orElseThrow(() -> new NotFoundSystemException("존재하지 않는 contract 입니다."));

        return RequestCommand.builder()
                .orderId(createCommand.getOrderId())
                .orderName(createCommand.getOrderName())
                .amount(createCommand.getAmount())
                .cardCompany(CardCompany.valueOf(createCommand.getCardCompany()))
                .customer(createCustomer(createCommand))
                .contractId(contract.getId())
                .successUrl(createCommand.getSuccessUrl())
                .failUrl(createCommand.getFailUrl())
                .build();
    }

    private Customer createCustomer(PaymentRequestCreateCommand createCommand) {
        return Customer.builder()
                .customerEmail(createCommand.getCustomerEmail())
                .customerName(createCommand.getCustomerName())
                .build();
    }
}
