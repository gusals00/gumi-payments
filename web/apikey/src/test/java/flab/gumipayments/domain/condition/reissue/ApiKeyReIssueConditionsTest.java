package flab.gumipayments.domain.condition.reissue;

import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.ApiKeyType.PROD;
import static flab.gumipayments.domain.ApiKeyType.TEST;
import static flab.gumipayments.domain.ReIssueFactor.*;
import static flab.gumipayments.domain.condition.ApiKeyReIssueConditions.*;
import static flab.gumipayments.support.specification.Condition.and;
import static flab.gumipayments.support.specification.Condition.or;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyReIssueConditionsTest {
    private Condition<ReIssueFactor> sut =
            or(
                    and(IS_TEST_API_KEY, EXIST_ACCOUNT, EXIST_API_KEY),
                    and(IS_PROD_API_KEY, EXIST_ACCOUNT, IS_CONTRACT_COMPLETE, EXIST_API_KEY)
            );

    private ReIssueFactorBuilder reIssueFactorBuilder;

    @BeforeEach
    void setup() {
        reIssueFactorBuilder = ReIssueFactor.builder();
    }

    @Test
    @DisplayName("조건: 테스트 API키 재발급 조건을 만족한다.")
    void testIssueCondition() {
        ReIssueFactor reIssueFactor = reIssueFactorBuilder
                .apiKeyType(TEST)
                .accountExist(true)
                .apiKeyExist(true).build();

        boolean result = sut.isSatisfiedBy(reIssueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용 API키 재발급 조건을 만족한다.")
    void prodIssueCondition() {
        ReIssueFactor reIssueFactor = reIssueFactorBuilder
                .apiKeyType(PROD)
                .accountExist(true)
                .apiKeyExist(true)
                .contractCompleteExist(true).build();

        boolean result = sut.isSatisfiedBy(reIssueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 실서비스용과 테스트용 API키 재발급 조건을 모두 만족하지 않는다.")
    void IssueCondition() {
        ReIssueFactor reIssueFactor = reIssueFactorBuilder
                .build();

        boolean result = sut.isSatisfiedBy(reIssueFactor);

        assertThat(result).isFalse();
    }
}