package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.gumipayments.domain.contract.ContractStatus.*;


@Service
@RequiredArgsConstructor
public class ApiKeyIssueConditionService {

    private final AccountRepository accountRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ContractRepository contractRepository;

    private final ApiKeyIssueAvailableCheck apiKeyIssueAvailableCheck;

    @Transactional
    public boolean isSatisfy(ApiKeyType type, Long accountId) {
        ApiKeyIssueAvailableCheckRequest issueAvailableCheckRequest = createIssueAvailableCheckRequest(type, accountId);

        return apiKeyIssueAvailableCheck.test(issueAvailableCheckRequest);
    }

    private ApiKeyIssueAvailableCheckRequest createIssueAvailableCheckRequest(ApiKeyType type, Long accountId) {
        return  ApiKeyIssueAvailableCheckRequest.builder()
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, type))
                .accountExist(accountRepository.existsById(accountId))
                .contractCompleteExist(contractRepository.existsByAccountIdAndStatus(accountId, CONTRACT_COMPLETE))
                .build();
    }
}
