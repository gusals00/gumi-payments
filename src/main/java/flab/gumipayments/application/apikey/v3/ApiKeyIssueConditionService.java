package flab.gumipayments.application.apikey.v3;

import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.apikey.ApiKeyRepository;
import flab.gumipayments.domain.apikey.ApiKeyType;
import flab.gumipayments.domain.contract.Contract;
import flab.gumipayments.domain.contract.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static flab.gumipayments.application.apikey.v3.ProductIssueConditions.*;
import static flab.gumipayments.application.apikey.v3.TestIssueConditions.*;


@Service
@RequiredArgsConstructor
public class ApiKeyIssueConditionService {

    private final AccountRepository accountRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ContractRepository contractRepository;

    private final TestIssueConditions testIssueConditions;
    private final ProductIssueConditions productIssueConditions;

    @Transactional
    public boolean isSatisfy(ApiKeyType type, Long accountId) {
        TestApiKeyIssueCondition testApiKeyIssueCondition = testIssueConditions.getTestApiKeyIssueCondition();
        ProductApiKeyIssueCondition productApiKeyIssueCondition = productIssueConditions.getProductApiKeyIssueCondition();

        if(testApiKeyIssueCondition.test(createTestIssueCandidate(type, accountId)) || productApiKeyIssueCondition.test(createProductIssueCandidate(type,accountId))){
            return true;
        }
        return false;
    }

    private TestIssueCandidate createTestIssueCandidate(ApiKeyType type, Long accountId) {
        return TestIssueCandidate.builder()
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, type))
                .accountExist(accountRepository.existsById(accountId)).build();
    }

    private ProductIssueCandidate createProductIssueCandidate(ApiKeyType type, Long accountId) {
        Contract contract = contractRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NoSuchElementException("계약이 존재하지 않습니다."));

        return  ProductIssueCandidate.builder()
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, type))
                .accountExist(accountRepository.existsById(accountId))
                .contract(contract).build();
    }

    @Transactional
    public boolean isSatisfyConditionByTestApiKey(Long accountId){
        TestIssueCandidate testIssueCandidate = createTestIssueCandidate(ApiKeyType.TEST, accountId);

        return testIssueConditions.getTestApiKeyIssueCondition().test(testIssueCandidate);
    }

    @Transactional
    public boolean isSatisfyConditionByProductionApiKey(Long accountId){
        Contract contract = contractRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NoSuchElementException("계약이 존재하지 않습니다."));

        ProductIssueCandidate productIssueCandidate = ProductIssueCandidate.builder()
                .apiKeyExist(apiKeyRepository.existsByAccountIdAndType(accountId, ApiKeyType.PRODUCTION))
                .accountExist(accountRepository.existsById(accountId))
                .contract(contract).build();

        return productIssueConditions.getProductApiKeyIssueCondition().test(productIssueCandidate);
    }

}
