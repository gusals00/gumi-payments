package flab.gumipayments.domain.apikey.condition.reissue;

import flab.gumipayments.domain.apikey.ReIssueCommand;
import flab.gumipayments.domain.apikey.ApiKeyReIssueCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.condition.reissue.ApiKeyReIssueConditions.EXIST_ACCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

class ExistAccountConditionTest {

    private ReIssueCommand.ReIssueCommandBuilder reIssueCommandBuilder;
    private ApiKeyReIssueCondition sut;
    @BeforeEach
    void setup() {
        reIssueCommandBuilder = ReIssueCommand.builder();
    }

    @Test
    @DisplayName("조건: 계정이 존재하면 발급 조건을 만족한다.")
    void accountExist01() {
        ReIssueCommand issueCommand = reIssueCommandBuilder.accountExist(true).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계정이 존재하지 않으면 발급 조건을 만족하지 않는다.")
    void accountExist02() {
        ReIssueCommand issueCommand = reIssueCommandBuilder.accountExist(false).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueCommand);

        assertThat(result).isFalse();
    }

}