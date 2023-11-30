package flab.gumipayments.application.apikey;

import flab.gumipayments.domain.apikey.*;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static flab.gumipayments.domain.apikey.ApiKeyReIssueCondition.*;
import static flab.gumipayments.domain.apikey.ApiKeyReIssueCondition.and;
import static flab.gumipayments.domain.apikey.ApiKeyReIssueCondition.not;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.*;


@Service
@RequiredArgsConstructor
public class ApiKeyReIssueRequesterApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyCreatorApplication apiKeyCreatorApplication;

    @Setter
    private ApiKeyReIssueCondition apiKeyReIssueCondition =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, EXIST_API_KEY),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, EXIST_API_KEY)
            );

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


    private ApiKeyCreateCommand convert(ApiKeyReIssueCommand issueCommand) {
        return ApiKeyCreateCommand.builder()
                .keyType(issueCommand.getApiKeyType())
                .expireDate(issueCommand.getExpireDate())
                .accountId(issueCommand.getAccountId())
                .build();
    }

    private void deleteApiKey(ApiKeyReIssueCommand reIssueCommand) {
        // 기존 api 조회
        ApiKey apiKey = apiKeyRepository.findByAccountIdAndType(reIssueCommand.getAccountId(), reIssueCommand.getApiKeyType())
                .orElseThrow(() -> new NoSuchElementException("api키가 존재하지 않습니다."));

        //기존 api 키 삭제
        apiKeyRepository.delete(apiKey);
    }
}