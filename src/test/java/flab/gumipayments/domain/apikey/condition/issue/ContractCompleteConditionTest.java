package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.IssueFactor;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.IssueFactor.*;
import static flab.gumipayments.domain.apikey.condition.issue.ApiKeyIssueConditions.IS_CONTRACT_COMPLETE;

import static org.assertj.core.api.Assertions.assertThat;

class ContractCompleteConditionTest {

    private IssueFactorBuilder issueFactorBuilder;
    private ApiKeyIssueCondition sut;

    @BeforeEach
    void setup() {
        issueFactorBuilder = IssueFactor.builder();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 발급 조건을 만족한다.")
    void contractComplete01() {
        IssueFactor issueFactor = issueFactorBuilder.contractCompleteExist(true).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 않으면 발급 조건을 만족하지 않는다.")
    void contractComplete02() {
        IssueFactor issueFactor = issueFactorBuilder.contractCompleteExist(false).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isFalse();
    }
}