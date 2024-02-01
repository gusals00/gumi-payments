package flab.gumipayments.domain.condition.issue;

import flab.gumipayments.domain.IssueFactor;
import flab.gumipayments.support.specification.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.IssueFactor.*;
import static flab.gumipayments.domain.condition.issue.ApiKeyIssueConditions.EXIST_ACCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

class ExistAccountConditionTest {

    private IssueFactorBuilder issueFactorBuilder;
    private Condition<IssueFactor> sut;
    @BeforeEach
    void setup() {
        issueFactorBuilder = builder();
    }

    @Test
    @DisplayName("조건: 계정이 존재하면 발급 조건을 만족한다.")
    void accountExist01() {
        IssueFactor issueFactor = issueFactorBuilder.accountExist(true).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("조건: 계정이 존재하지 않으면 발급 조건을 만족하지 않는다.")
    void accountExist02() {
        IssueFactor issueFactor = issueFactorBuilder.accountExist(false).build();
        sut = EXIST_ACCOUNT;

        boolean result = sut.isSatisfiedBy(issueFactor);

        assertThat(result).isFalse();
    }
}