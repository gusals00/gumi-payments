package flab.gumipayments.domain.condition.reissue;

import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.ReIssueFactor.*;
import static flab.gumipayments.domain.condition.reissue.ApiKeyReIssueConditions.IS_CONTRACT_COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;

class ContractCompleteConditionTest {

    private ReIssueFactorBuilder reIssueFactorBuilder;
    private Condition<ReIssueFactor> sut;

    @BeforeEach
    void setup() {
        reIssueFactorBuilder = ReIssueFactor.builder();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 재발급 조건을 만족한다.")
    void contractComplete01() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.contractCompleteExist(true).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 않으면 재발급 조건을 만족하지 않는다.")
    void contractComplete02() {
        ReIssueFactor issueCommand = reIssueFactorBuilder.contractCompleteExist(false).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }

}