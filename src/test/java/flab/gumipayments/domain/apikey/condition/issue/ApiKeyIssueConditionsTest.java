package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.IssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.condition.issue.ApiKeyIssueConditions.*;
import static flab.gumipayments.domain.apikey.ApiKeyType.*;

import static org.assertj.core.api.Assertions.*;
import static flab.gumipayments.domain.apikey.ApiKeyIssueCondition.*;

class ApiKeyIssueConditionsTest {

    private ApiKeyIssueCondition sut =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, not(EXIST_API_KEY)),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, not(EXIST_API_KEY))
            );

    private ApiKeyIssueCommandBuilder apiKeyIssueCommandBuilder;

    @BeforeEach
    void setup() {
        apiKeyIssueCommandBuilder = IssueCommand.builder();
    }

    @Test
    @DisplayName("조건: 테스트 API키 발급 조건을 만족한다.")
    void testIssueCondition() {
        IssueCommand issueCommand = apiKeyIssueCommandBuilder.apiKeyType(TEST)
                .accountExist(true)
                .apiKeyExist(false).build();

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용 API키 발급 조건을 만족한다.")
    void prodIssueCondition() {
        IssueCommand issueCommand = apiKeyIssueCommandBuilder.apiKeyType(PROD)
                .accountExist(true)
                .apiKeyExist(false)
                .contractCompleteExist(true).build();

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용과 테스트용 API키 발급 조건을 모두 만족하지 않는다.")
    void IssueCondition() {
        IssueCommand issueCommand = apiKeyIssueCommandBuilder.build();

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }
}