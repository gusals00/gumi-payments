package flab.gumipayments.domain;

import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupAcceptTimeoutException;
import flab.gumipayments.domain.signup.SignupIllegalStatusException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static flab.gumipayments.domain.signup.Signup.SignupBuilder;
import static flab.gumipayments.domain.signup.SignupStatus.ACCEPT;
import static flab.gumipayments.domain.signup.SignupStatus.ACCOUNT_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SignupTest {

    private Signup sut;
    private MockedStatic<LocalDateTime> localDateMockedStatic;
    private SignupBuilder sutBuilder;

    private
    @BeforeEach
    void setup() {
        sutBuilder = Signup.builder();
        freezeLocalDateTimeNow();
    }

    @AfterEach
    void close() {
        localDateMockedStatic.close();
    }

    private void freezeLocalDateTimeNow() {
        LocalDateTime date = LocalDateTime.of(2023, 10, 23, 0, 0);
        localDateMockedStatic = Mockito.mockStatic(LocalDateTime.class);
        localDateMockedStatic.when(LocalDateTime::now).thenReturn(date);
    }

    @Test
    @DisplayName("예외: 기간이 만료된 인증은 실패한다.")
    void acceptTimeout() {
        sut = sutBuilder
                .expireDate(LocalDateTime.now().minusDays(1)).build();

        assertThatThrownBy(() -> sut.accept()).isInstanceOf(SignupAcceptTimeoutException.class);
    }

    @Test
    @DisplayName("예외: 인증되지 않았으면 계정 생성은 실패한다")
    void notAccept() {
        sut = sutBuilder.build();

        assertThatThrownBy(() -> sut.accountCreated()).isInstanceOf(SignupIllegalStatusException.class);
    }

    @Test
    @DisplayName("예외: 계정이 이미 존재하면 계정 생성은 실패한다.")
    void alreadyAccountCreated() {
        sut = alreadyAccountCreatedSignup();

        assertThatThrownBy(() -> sut.accountCreated()).isInstanceOf(SignupIllegalStatusException.class);
    }

    @Test
    @DisplayName("성공: 가입 요청이 인증에 성공한다")
    void acceptSignup() {
        sut = sutBuilder
                .expireDate(LocalDateTime.now().plusDays(1)).build();

        sut.accept();

        assertThat(sut.getStatus()).isEqualTo(ACCEPT);
    }

    @Test
    @DisplayName("성공: 인증이 되었으면 계정 생성에 성공한다.")
    void accountCreate() {
        sut = acceptedSignup();

        sut.accountCreated();

        assertThat(sut.getStatus()).isEqualTo(ACCOUNT_CREATED);
    }

    private Signup alreadyAccountCreatedSignup() {
        Signup signup =  sutBuilder
                .expireDate(LocalDateTime.now().plusDays(1)).build();
        signup.accept();
        signup.accountCreated();

        return signup;
    }

    private Signup acceptedSignup() {
        Signup signup = sutBuilder
                .expireDate(LocalDateTime.now().plusDays(1)).build();
        signup.accept();
        return signup;
    }
}