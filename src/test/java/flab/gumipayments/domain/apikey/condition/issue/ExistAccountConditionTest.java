package flab.gumipayments.domain.apikey.condition.issue;

import flab.gumipayments.domain.apikey.IssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.IssueCommand.*;
import static flab.gumipayments.domain.apikey.condition.issue.ApiKeyIssueConditions.EXIST_ACCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

class ExistAccountConditionTest {

    private IssueCommandBuilder issueCommandBuilder;
    private ApiKeyIssueCondition sut;
    @BeforeEach
    void setup() {
        issueCommandBuilder = builder();
    }

    @Test
    @DisplayName("조건: 계정이 존재하면 발급 조건을 만족한다.")
    void accountExist01() {
        IssueCommand issueCommand = issueCommandBuilder.accountExist(true).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계정이 존재하지 않으면 발급 조건을 만족하지 않는다.")
    void accountExist02() {
        IssueCommand issueCommand = issueCommandBuilder.accountExist(false).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }
}