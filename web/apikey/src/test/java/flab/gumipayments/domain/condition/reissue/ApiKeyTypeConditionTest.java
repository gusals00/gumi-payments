package flab.gumipayments.domain.condition.reissue;

import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.ApiKeyType.PROD;
import static flab.gumipayments.domain.ApiKeyType.TEST;
import static flab.gumipayments.domain.ReIssueFactor.*;
import static flab.gumipayments.domain.condition.ApiKeyReIssueConditions.IS_PROD_API_KEY;
import static flab.gumipayments.domain.condition.ApiKeyReIssueConditions.IS_TEST_API_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyTypeConditionTest{

    private ReIssueFactorBuilder reIssueFactorBuilder;
    private Condition<ReIssueFactor> sut;

    @BeforeEach
    void setup() {
        reIssueFactorBuilder = ReIssueFactor.builder();
    }

    @Test
    @DisplayName("조건: API 키 타입 = TEST 이면 재발급 조건을 만족한다.")
    void apiKeyTest01() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.apiKeyType(TEST).build();
        sut = IS_TEST_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: API 키 타입 = TEST 가 아니면 재발급 조건을 만족하지 않는다.")
    void apiKeyTest02() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.apiKeyType(PROD).build();
        sut = IS_TEST_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("조건: API 키 타입 = PROD 이면 재발급 조건을 만족한다.")
    void apiKeyPROD01() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.apiKeyType(PROD).build();
        sut = IS_PROD_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: API 키 타입 = PROD 가 아니면 재발급 조건을 만족하지 않는다.")
    void apiKeyPROD02() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.apiKeyType(TEST).build();
        sut = IS_PROD_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }
}