package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.*;
import flab.gumipayments.domain.apikey.condition.issue.ApiKeyIssueConditions;
import flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions;
import flab.gumipayments.support.specification.Condition;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


import static flab.gumipayments.support.specification.Condition.*;


@Service
@RequiredArgsConstructor
public class ApiKeyIssueRequesterApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyCreatorApplication apiKeyCreatorApplication;

    @Setter
    private Condition<ApiKeyIssueCommand> apiKeyIssueCondition =
            or(
                    and(ApiKeyIssueConditions.IS_TEST_API_KEY, ApiKeyIssueConditions.EXIST_ACCOUNT, not(ApiKeyIssueConditions.EXIST_API_KEY)),
                    and(ApiKeyIssueConditions.IS_PROD_API_KEY, ApiKeyIssueConditions.EXIST_ACCOUNT, ApiKeyIssueConditions.IS_CONTRACT_COMPLETE, not(ApiKeyIssueConditions.EXIST_API_KEY))
            );

    @Setter
    private Condition<ApiKeyReIssueCommand> apiKeyReIssueCondition =
            or(
                    and(ApiKeyReIssueConditions.IS_TEST_API_KEY, ApiKeyReIssueConditions.EXIST_ACCOUNT, not(ApiKeyReIssueConditions.EXIST_API_KEY)),
                    and(ApiKeyReIssueConditions.IS_PROD_API_KEY, ApiKeyReIssueConditions.EXIST_ACCOUNT, ApiKeyReIssueConditions.IS_CONTRACT_COMPLETE, ApiKeyReIssueConditions.EXIST_API_KEY)
            );

    // TODO issueCommand 안에 있는 exist들 찾는 코드 작성해야 함
    @Transactional
    public ApiKeyPair issueApiKey(ApiKeyIssueCommand issueCommand) {

        // 발급 조건 확인
        if (!apiKeyIssueCondition.isSatisfiedBy(issueCommand)) {
            throw new ApiKeyIssueException("api 키 발급 조건이 올바르지 않습니다.");
        }

        // api 키 생성
        ApiKeyResponse apiKeyResponse = apiKeyCreatorApplication.create(convert(issueCommand));

        //api 키 저장
        apiKeyRepository.save(apiKeyResponse.getApiKey());

        return apiKeyResponse.getApiKeyPair();
    }

    @Transactional
    //api 키 재발급
    public ApiKeyPair reIssueApiKey(ApiKeyReIssueCommand reIssueCommand) {
        // 재발급 조건
        if (!apiKeyReIssueCondition.isSatisfiedBy(reIssueCommand)) {
            throw new ApiKeyIssueException("api 키 재발급 조건이 올바르지 않습니다.");
        }

        // 기존 api 키 삭제
        deleteApiKey(reIssueCommand);

        // api 키 생성
        ApiKeyResponse apiKeyResponse = apiKeyCreatorApplication.create(convert(reIssueCommand));

        //api 키 저장
        apiKeyRepository.save(apiKeyResponse.getApiKey());

        return apiKeyResponse.getApiKeyPair();
    }

    private void deleteApiKey(ApiKeyReIssueCommand reIssueCommand) {
        // 기존 api 조회
        ApiKey apiKey = apiKeyRepository.findByAccountIdAndType(reIssueCommand.getAccountId(), reIssueCommand.getApiKeyType())
                .orElseThrow(() -> new NoSuchElementException("api키가 존재하지 않습니다."));

        //기존 api 키 삭제
        apiKeyRepository.delete(apiKey);
    }

    private ApiKeyCreateCommand convert(ApiKeyIssueCommand issueCommand) {
        return ApiKeyCreateCommand.builder()
                .apiKeyType(issueCommand.getApiKeyType())
                .expireDate(issueCommand.getExpireDate())
                .accountId(issueCommand.getAccountId())
                .build();
    }

    private ApiKeyCreateCommand convert(ApiKeyReIssueCommand issueCommand) {
        return ApiKeyCreateCommand.builder()
                .apiKeyType(issueCommand.getApiKeyType())
                .expireDate(issueCommand.getExpireDate())
                .accountId(issueCommand.getAccountId())
                .build();
    }
}
