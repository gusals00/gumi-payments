package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.IssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.IssueCommand.builder;
import static flab.gumipayments.domain.apikey.condition.issue.ApiKeyIssueConditions.IS_CONTRACT_COMPLETE;

import static org.assertj.core.api.Assertions.assertThat;

class ContractCompleteConditionTest {

    private ApiKeyIssueCommandBuilder apiKeyIssueCommandBuilder;
    private ApiKeyIssueCondition sut;

    @BeforeEach
    void setup() {
        apiKeyIssueCommandBuilder = builder();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 발급 조건을 만족한다.")
    void contractComplete01() {
        IssueCommand issueCommand = apiKeyIssueCommandBuilder.contractCompleteExist(true).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 않으면 발급 조건을 만족하지 않는다.")
    void contractComplete02() {
        IssueCommand issueCommand = apiKeyIssueCommandBuilder.contractCompleteExist(false).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }
}