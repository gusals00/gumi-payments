package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.ApiKeyIssueCommand;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.ApiKeyIssueCommand.*;
import static flab.gumipayments.domain.apikey.condition.issue.ApiKeyIssueConditions.*;
import static flab.gumipayments.domain.apikey.ApiKeyType.*;
import static flab.gumipayments.support.specification.Condition.and;
import static flab.gumipayments.support.specification.Condition.or;
import static flab.gumipayments.support.specification.Condition.not;
import static org.assertj.core.api.Assertions.*;

class ApiKeyIssueConditionsTest {

    private Condition sut =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, not(EXIST_API_KEY)),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, not(EXIST_API_KEY))
            );

    private ApiKeyIssueCommandBuilder apiKeyIssueCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyIssueCommandBuilder = ApiKeyIssueCommand.builder();
    }

    @Test
    @DisplayName("조건: 테스트 API키 조건을 만족한다.")
    void testIssueCondition() {
        ApiKeyIssueCommand issueCommand = apiKeyIssueCommandBuilder.apiKeyType(TEST)
                .accountExist(true)
                .apiKeyExist(false).build();

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용 API키 조건을 만족한다.")
    void prodIssueCondition() {
        ApiKeyIssueCommand issueCommand = apiKeyIssueCommandBuilder.apiKeyType(PRODUCTION)
                .accountExist(true)
                .apiKeyExist(false)
                .contractCompleteExist(true).build();

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용과 테스트용 API키 조건을 모두 만족하지 않는다.")
    void IssueCondition() {
        ApiKeyIssueCommand issueCommand = apiKeyIssueCommandBuilder.build();

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }
}