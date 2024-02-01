package flab.gumipayments.domain.condition.issue;

import flab.gumipayments.domain.IssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.ApiKeyType.*;
import static flab.gumipayments.domain.IssueFactor.*;
import static flab.gumipayments.domain.condition.issue.ApiKeyIssueConditions.*;
import static flab.gumipayments.support.specification.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyIssueConditionsTest {

    private Condition<IssueFactor> sut =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, not(EXIST_API_KEY)),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, not(EXIST_API_KEY))
            );
    private IssueFactorBuilder issueFactorBuilder;

    @BeforeEach
    void setup() {
        issueFactorBuilder = IssueFactor.builder();
    }

    @Test
    @DisplayName("조건: 테스트 API키 발급 조건을 만족한다.")
    void testIssueCondition() {
        IssueFactor issueFactor = issueFactorBuilder.apiKeyType(TEST)
                .accountExist(true)
                .apiKeyExist(false).build();

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용 API키 발급 조건을 만족한다.")
    void prodIssueCondition() {
        IssueFactor issueFactor = issueFactorBuilder.apiKeyType(PROD)
                .accountExist(true)
                .apiKeyExist(false)
                .contractCompleteExist(true).build();

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용과 테스트용 API키 발급 조건을 모두 만족하지 않는다.")
    void IssueCondition() {
        IssueFactor issueFactor = issueFactorBuilder.build();

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isFalse();
    }
}