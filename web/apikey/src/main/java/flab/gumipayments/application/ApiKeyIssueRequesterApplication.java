package flab.gumipayments.application;

import flab.gumipayments.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.gumipayments.domain.ApiKeyIssuePolicy.*;
import static flab.gumipayments.domain.condition.issue.ApiKeyIssueConditions.*;
import static flab.gumipayments.support.specification.Condition.*;


@Service
@RequiredArgsConstructor
public class ApiKeyIssueRequesterApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyCreatorRequesterApplication apiKeyCreatorRequesterApplication;

    @Setter
    private ApiKeyIssuePolicy issuePolicy = of(
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, not(EXIST_API_KEY)),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, not(EXIST_API_KEY))
            )
    );

    @Transactional
    public ApiKeyPair issueApiKey(IssueFactor issueFactor) {

        // 발급 조건 확인
        if (!issuePolicy.check(issueFactor)) {
            throw new ApiKeyIssueException("api 키 발급 조건이 올바르지 않습니다.");
        }

        // api 키 생성
        ApiKeyResponse apiKeyResponse = apiKeyCreatorRequesterApplication.create(convert(issueFactor));

        //api 키 저장
        apiKeyRepository.save(apiKeyResponse.getApiKey());

        return apiKeyResponse.getApiKeyPair();
    }

    private ApiKeyCreateCommand convert(IssueFactor issueFactor) {
        return ApiKeyCreateCommand.builder()
                .keyType(issueFactor.getApiKeyType())
                .expireDate(issueFactor.getExpireDate())
                .accountId(issueFactor.getAccountId())
                .build();
    }
}
