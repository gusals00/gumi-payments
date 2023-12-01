package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.*;
import flab.gumipayments.domain.apikey.ApiKeyCreateCommand;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


import static flab.gumipayments.domain.apikey.ApiKeyIssueCondition.*;
import static flab.gumipayments.domain.apikey.condition.issue.ApiKeyIssueConditions.*;


@Service
@RequiredArgsConstructor
public class ApiKeyIssueRequesterApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyCreatorApplication apiKeyCreatorApplication;

    @Setter
    private ApiKeyIssueCondition issueCondition =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, not(EXIST_API_KEY)),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, not(EXIST_API_KEY))
            );

    @Transactional
    public ApiKeyPair issueApiKey(IssueCommand issueCommand) {

        // 발급 조건 확인
        if (!issueCondition.isSatisfiedBy(issueCommand)) {
            throw new ApiKeyIssueException("api 키 발급 조건이 올바르지 않습니다.");
        }

        // api 키 생성
        ApiKeyResponse apiKeyResponse = apiKeyCreatorApplication.create(convert(issueCommand));

        //api 키 저장
        apiKeyRepository.save(apiKeyResponse.getApiKey());

        return apiKeyResponse.getApiKeyPair();
    }

    private ApiKeyCreateCommand convert(IssueCommand issueCommand) {
        return ApiKeyCreateCommand.builder()
                .keyType(issueCommand.getApiKeyType())
                .expireDate(issueCommand.getExpireDate())
                .accountId(issueCommand.getAccountId())
                .build();
    }
}
