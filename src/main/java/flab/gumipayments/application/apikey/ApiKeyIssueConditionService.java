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

    private final ApiKeyIssueConditionChecker apiKeyIssueChecker;

    @Transactional
    public boolean isSatisfy(ApiKeyType type, Long accountId) {
        ApiKeyIssueCheckRequest request = createIssueCheckRequest(type, accountId);
        return apiKeyIssueChecker.check(request);
    }

    private ApiKeyIssueCheckRequest createIssueCheckRequest(ApiKeyType type, Long accountId) {
        return  ApiKeyIssueCheckRequest.builder()
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, type))
                .accountExist(accountRepository.existsById(accountId))
                .contractCompleteExist(contractRepository.existsByAccountIdAndStatus(accountId, CONTRACT_COMPLETE))
                .build();
    }
}
