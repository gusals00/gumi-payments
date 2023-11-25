package flab.gumipayments.application.apikey.condition;

import flab.gumipayments.application.apikey.ApiKeyIssueCommand;
import flab.gumipayments.application.apikey.condition.specification.ApiKeyIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.application.apikey.ApiKeyIssueCommand.*;
import static flab.gumipayments.application.apikey.ApiKeyIssueCommand.builder;
import static flab.gumipayments.application.apikey.condition.ApiKeyIssueConditions.IS_CONTRACT_COMPLETE;

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
        ApiKeyIssueCommand issueCommand = apiKeyIssueCommandBuilder.contractCompleteExist(true).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계약이 완료되면 않으면 발급 조건을 만족하지 않는다.")
    void contractComplete02() {
        ApiKeyIssueCommand issueCommand = apiKeyIssueCommandBuilder.contractCompleteExist(false).build();
        sut = IS_CONTRACT_COMPLETE;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }
}