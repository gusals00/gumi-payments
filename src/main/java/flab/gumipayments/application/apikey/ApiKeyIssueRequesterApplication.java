package flab.gumipayments.application.apikey;

import flab.gumipayments.application.apikey.condition.specification.ApiKeyIssueCondition;
import flab.gumipayments.domain.apikey.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static flab.gumipayments.application.apikey.condition.ApiKeyIssueConditions.*;
import static flab.gumipayments.application.apikey.condition.ContractCompleteCondition.*;


@Service
@RequiredArgsConstructor
public class ApiKeyIssueRequesterApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyFactory apiKeyFactory;

    private final ApiKeyIssueCondition apiKeyIssueCondition =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, not(EXIST_API_KEY)),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, not(EXIST_API_KEY))
            );

    // TODO issueCommand 안에 있는 exist들 찾는 코드 작성해야 함
    @Transactional
    public ApiKeyPair issueApiKey(ApiKeyIssueCommand issueCommand) {

        // 발급 조건 확인
        if(!apiKeyIssueCondition.isSatisfiedBy(issueCommand)){
            throw new ApiKeyIssueException("api 키 발급 조건이 올바르지 않습니다.");
        }

        // api 키 생성
        ApiKeyResponse apiKeyResponse = apiKeyFactory.create(convert(issueCommand));

        //api 키 저장
        apiKeyRepository.save(apiKeyResponse.getApiKey());

        return apiKeyResponse.getApiKeyPair();
    }

    @Transactional
    //api 키 재발급
    public ApiKeyPair reIssueApiKey(ApiKeyReIssueCommand reIssueCommand) {
        // 재발급 조건
        // TODO 재발급 조건 생성

        // 기존 api 키 삭제
        deleteApiKey(reIssueCommand);

        // api 키 생성
        ApiKeyResponse apiKeyResponse = apiKeyFactory.create(convert(reIssueCommand));

        //api 키 저장
        apiKeyRepository.save(apiKeyResponse.getApiKey());

        return apiKeyResponse.getApiKeyPair();
    }

    private void deleteApiKey(ApiKeyReIssueCommand reIssueCommand) {
        // 기존 api 조회
        ApiKey apiKey = apiKeyRepository.findByAccountIdAndType(reIssueCommand.getAccountId(), reIssueCommand.getKeyType())
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
                .apiKeyType(issueCommand.getKeyType())
                .expireDate(issueCommand.getExpireDate())
                .accountId(issueCommand.getAccountId())
                .build();
    }
}
