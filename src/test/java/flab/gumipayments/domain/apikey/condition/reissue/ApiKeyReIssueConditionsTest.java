package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.ApiKeyReIssueCommand.*;
import static flab.gumipayments.domain.apikey.ApiKeyReIssueCondition.and;
import static flab.gumipayments.domain.apikey.ApiKeyReIssueCondition.or;
import static flab.gumipayments.domain.apikey.ApiKeyType.PROD;
import static flab.gumipayments.domain.apikey.ApiKeyType.TEST;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.EXIST_ACCOUNT;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.EXIST_API_KEY;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.IS_CONTRACT_COMPLETE;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.IS_PROD_API_KEY;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.IS_TEST_API_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyReIssueConditionsTest {
    private ApiKeyReIssueCondition sut =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, EXIST_API_KEY),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, EXIST_API_KEY)
            );

    private ApiKeyReIssueCommandBuilder apiKeyReIssueCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyReIssueCommandBuilder = ApiKeyReIssueCommand.builder();
    }

    @Test
    @DisplayName("조건: 테스트 API키 재발급 조건을 만족한다.")
    void testIssueCondition() {
        ApiKeyReIssueCommand reIssueCommand = apiKeyReIssueCommandBuilder
                .apiKeyType(TEST)
                .accountExist(true)
                .apiKeyExist(true).build();

        boolean result = sut.isSatisfiedBy(reIssueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용 API키 재발급 조건을 만족한다.")
    void prodIssueCondition() {
        ApiKeyReIssueCommand reIssueCommand = apiKeyReIssueCommandBuilder
                .apiKeyType(PROD)
                .accountExist(true)
                .apiKeyExist(true)
                .contractCompleteExist(true).build();

        boolean result = sut.isSatisfiedBy(reIssueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용과 테스트용 API키 재발급 조건을 모두 만족하지 않는다.")
    void IssueCondition() {
        ApiKeyReIssueCommand reIssueCommand = apiKeyReIssueCommandBuilder
                .build();

        boolean result = sut.isSatisfiedBy(reIssueCommand);

        assertThat(result).isFalse();
    }
}