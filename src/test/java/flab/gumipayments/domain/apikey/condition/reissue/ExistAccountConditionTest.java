package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ApiKeyReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.EXIST_ACCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

class ExistAccountConditionTest {

    private ApiKeyReIssueCommand.ApiKeyReIssueCommandBuilder apiKeyReIssueCommandBuilder;
    private ApiKeyReIssueCondition sut;
    @BeforeEach
    void setup() {
        apiKeyReIssueCommandBuilder = ApiKeyReIssueCommand.builder();
    }

    @Test
    @DisplayName("조건: 계정이 존재하면 발급 조건을 만족한다.")
    void accountExist01() {
        ApiKeyReIssueCommand issueCommand = apiKeyReIssueCommandBuilder.accountExist(true).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계정이 존재하지 않으면 발급 조건을 만족하지 않는다.")
    void accountExist02() {
        ApiKeyReIssueCommand issueCommand = apiKeyReIssueCommandBuilder.accountExist(false).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }

}