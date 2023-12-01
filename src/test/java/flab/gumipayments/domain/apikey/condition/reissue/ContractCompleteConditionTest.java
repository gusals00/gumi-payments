package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.ReIssueCommand.*;
import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.IS_CONTRACT_COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;

class ContractCompleteConditionTest {

    private ReIssueCommandBuilder reIssueCommandBuilder;
    private ApiKeyReIssueCondition sut;

    @BeforeEach
    void setup() {
        reIssueCommandBuilder = ReIssueCommand.builder();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 재발급 조건을 만족한다.")
    void contractComplete01() {
        ReIssueCommand issueCommand = reIssueCommandBuilder.contractCompleteExist(true).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 않으면 재발급 조건을 만족하지 않는다.")
    void contractComplete02() {
        ReIssueCommand issueCommand = reIssueCommandBuilder.contractCompleteExist(false).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }

}