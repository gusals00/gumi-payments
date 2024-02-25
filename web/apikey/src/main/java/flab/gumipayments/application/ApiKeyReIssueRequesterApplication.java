package flab.gumipayments.application;

import flab.gumipayments.domain.*;
import flab.gumipayments.infrastructure.kafka.ApiKeyMessageDTO;
import flab.gumipayments.infrastructure.kafka.ApiKeyProducer;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.gumipayments.domain.ApiKeyReIssuePolicy.*;
import static flab.gumipayments.domain.condition.reissue.ApiKeyReIssueConditions.*;
import static flab.gumipayments.infrastructure.kafka.MessageType.DELETE;
import static flab.gumipayments.infrastructure.kafka.MessageType.INSERT;
import static flab.gumipayments.support.specification.Condition.and;
import static flab.gumipayments.support.specification.Condition.or;


@Service
@RequiredArgsConstructor
public class ApiKeyReIssueRequesterApplication {

    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyCreatorRequesterApplication apiKeyCreatorRequesterApplication;
    private final ApiKeyProducer producer;

    @Setter
    private ApiKeyReIssuePolicy reIssuePolicy = of(
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, EXIST_API_KEY),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, EXIST_API_KEY)
            )
    );

    @Transactional
    //api 키 재발급
    public ApiKeyPair reIssueApiKey(ReIssueFactor reIssueFactor) {
        // 재발급 조건
        if (!reIssuePolicy.check(reIssueFactor)) {
            throw new ApiKeyIssueException("api 키 재발급 조건이 올바르지 않습니다.");
        }

        // 기존 api 키 삭제
        deleteApiKey(reIssueFactor);

        // api 키 생성
        ApiKeyResponse apiKeyResponse = apiKeyCreatorRequesterApplication.create(convert(reIssueFactor));

        //api 키 저장
        ApiKey apiKey = apiKeyResponse.getApiKey();
        apiKeyRepository.save(apiKey);
        producer.sendApiKeyMessage(convertToMessage(apiKey), INSERT);

        return apiKeyResponse.getApiKeyPair();
    }


    private ApiKeyCreateCommand convert(ReIssueFactor issueCommand) {
        return ApiKeyCreateCommand.builder()
                .keyType(issueCommand.getApiKeyType())
                .expireDate(issueCommand.getExpireDate())
                .accountId(issueCommand.getAccountId())
                .build();
    }

    private void deleteApiKey(ReIssueFactor reIssueFactor) {
        // 기존 api 조회
        ApiKey apiKey = apiKeyRepository.findByAccountIdAndType(reIssueFactor.getAccountId(), reIssueFactor.getApiKeyType())
                .orElseThrow(() -> new NotFoundSystemException("api키가 존재하지 않습니다."));

        //기존 api 키 삭제
        apiKeyRepository.delete(apiKey);
        producer.sendApiKeyMessage(convertToMessage(apiKey), DELETE);
    }

    private ApiKeyMessageDTO convertToMessage(ApiKey apiKey) {
        return ApiKeyMessageDTO.builder()
                .id(apiKey.getId())
                .secretKey(apiKey.getSecretKey())
                .clientKey(apiKey.getClientKey())
                .accountId(apiKey.getAccountId())
                .type(apiKey.getType())
                .expireDate(apiKey.getExpireDate())
                .count(apiKey.getCount())
                .build();
    }
}