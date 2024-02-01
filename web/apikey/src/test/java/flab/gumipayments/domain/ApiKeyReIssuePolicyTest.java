package flab.gumipayments.domain;

import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.ReIssueFactor.*;
import static org.assertj.core.api.Assertions.assertThat;

class ApiKeyReIssuePolicyTest {
    private ApiKeyReIssuePolicy sut;
    private static Condition<ReIssueFactor> alwaysTrue = factor -> true;
    private static Condition<ReIssueFactor> alwaysFalse = factor -> false;
    private ReIssueFactorBuilder reIssueFactorBuilder;

    @BeforeEach
    void setup() {
        reIssueFactorBuilder = ReIssueFactor.builder();
    }

    @Test
    @DisplayName("정책: API 키 재발급 조건을 만족하면 API 키 재발급 정책을 만족한다.")
    void issuePolicy() {
        ReIssueFactor factor = reIssueFactorBuilder.build();

        sut = ApiKeyReIssuePolicy.of(alwaysTrue);

        assertThat(sut.check(factor)).isTrue();
    }

    @Test
    @DisplayName("정책: API 키 재발급 조건을 만족하지 못하면 API 키 재발급 정책을 만족하지 못한다.")
    void issuePolicyFail() {
        ReIssueFactor factor = reIssueFactorBuilder.build();

        sut = ApiKeyReIssuePolicy.of(alwaysFalse);

        assertThat(sut.check(factor)).isFalse();
    }
}