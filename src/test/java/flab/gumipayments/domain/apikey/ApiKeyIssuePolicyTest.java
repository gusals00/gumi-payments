package flab.gumipayments.domain.apikey;

import flab.gumipayments.support.specification.Condition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static flab.gumipayments.domain.apikey.IssueFactor.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ApiKeyIssuePolicyTest {

    private ApiKeyIssuePolicy sut;
    private static Condition<IssueFactor> alwaysTrue = factor -> true;
    private static Condition<IssueFactor> alwaysFalse = factor -> false;
    private IssueFactorBuilder issueFactorBuilder;

    @BeforeEach
    void setup() {
        issueFactorBuilder = IssueFactor.builder();
    }

    @Test
    @DisplayName("정책: API 키 발급 조건을 만족하면 API 키 발급 정책을 만족한다.")
    void issuePolicy(){
        IssueFactor factor = issueFactorBuilder.build();

        sut = ApiKeyIssuePolicy.of(alwaysTrue);

        assertThat(sut.check(factor)).isTrue();
    }

    @Test
    @DisplayName("정책: API 키 발급 조건을 만족하지 못하면 API 키 발급 정책을 만족하지 못한다.")
    void issuePolicyFail(){
        IssueFactor factor = issueFactorBuilder.build();

        sut = ApiKeyIssuePolicy.of(alwaysFalse);

        assertThat(sut.check(factor)).isFalse();
    }
}