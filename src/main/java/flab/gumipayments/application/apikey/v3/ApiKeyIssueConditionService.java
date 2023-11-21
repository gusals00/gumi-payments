package flab.gumipayments.application.apikey.v3;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.gumipayments.application.apikey.v3.IssueConditionsV3.*;
import static flab.gumipayments.domain.contract.ContractStatus.*;


@Service
@RequiredArgsConstructor
public class ApiKeyIssueConditionService {

    private final AccountRepository accountRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ContractRepository contractRepository;

    private final IssueConditionsV3 issueConditions;

    @Transactional
    public boolean isSatisfy(ApiKeyType type, Long accountId) {
        // 발급 조건 조회
        ApiKeyIssueConditionV3 productApiKeyIssueCondition = issueConditions.getProductApiKeyIssueCondition();
        ApiKeyIssueConditionV3 testApiKeyIssueCondition = issueConditions.getTestApiKeyIssueCondition();

        IssueCandidateV3 issueCandidate = createIssueCandidate(type, accountId);

        //발급 가능 여부 확인
        if(testApiKeyIssueCondition.test(issueCandidate) || productApiKeyIssueCondition.test(issueCandidate)){
            return true;
        }
        return false;
    }

    private IssueCandidateV3 createIssueCandidate(ApiKeyType type, Long accountId) {
        return  IssueCandidateV3.builder()
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, type))
                .accountExist(accountRepository.existsById(accountId))
                .contractCompleteExist(contractRepository.existsByAccountIdAndStatus(accountId, CONTRACT_COMPLETE))
                .build();
    }
}
