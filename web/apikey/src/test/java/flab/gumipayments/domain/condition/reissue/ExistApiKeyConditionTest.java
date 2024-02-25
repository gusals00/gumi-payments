package flab.gumipayments.domain.condition.reissue;

import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.ReIssueFactor.*;
import static flab.gumipayments.domain.condition.ApiKeyReIssueConditions.EXIST_API_KEY;
import static org.assertj.core.api.Assertions.assertThat;

class ExistApiKeyConditionTest {
    private ReIssueFactorBuilder reIssueFactorBuilder;
    private Condition<ReIssueFactor> sut;

    @BeforeEach
    void setup() {
        reIssueFactorBuilder = ReIssueFactor.builder();
    }

    @Test
    @DisplayName("조건: API 키가 존재하면 발급 조건을 만족한다.")
    void keyExist01() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.apiKeyExist(true).build();
        sut = EXIST_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: API 키가 존재하지 않으면 발급 조건을 만족하지 않는다.")
    void keyExist02() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.apiKeyExist(false).build();
        sut = EXIST_API_KEY;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }
}