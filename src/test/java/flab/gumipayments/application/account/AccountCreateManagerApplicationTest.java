package flab.gumipayments.application.account;

import flab.gumipayments.application.NotFoundException;
import flab.gumipayments.application.account.AccountCreateManagerApplication;
import flab.gumipayments.domain.account.AccountCreateCommand;
import flab.gumipayments.domain.account.AccountFactory;
import flab.gumipayments.domain.account.AccountRepository;
import flab.gumipayments.domain.signup.Signup;
import flab.gumipayments.domain.signup.SignupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static flab.gumipayments.domain.account.AccountCreateCommand.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountCreateManagerApplicationTest {

    @Mock
    private AccountFactory accountFactory;
    @InjectMocks
    private AccountCreateManagerApplication sut;
    @Mock
    private SignupRepository signupRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountCreateCommandBuilder accountCreateCommandBuilder;

    private Signup.SignupBuilder signupBuilder;

    @BeforeEach
    void setUp() {
        accountCreateCommandBuilder = AccountCreateCommand.builder();
        signupBuilder = Signup.builder();
    }

    @Test
    @DisplayName("예외: 가입 요청이 존재하지 않으면 계정 생성이 실패한다.")
    void accountNotExist() {
        Long signupId = 1234L;
        when(signupRepository.findById(signupId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.create(accountCreateCommandBuilder.build(), signupId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("signup이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("성공: 계정 생성을 성공한다.")
    void accountCreate() {
        AccountCreateCommand accountCreateCommand = accountCreateCommandBuilder
                .name("성호창").password("1234").build();
        when(signupRepository.findById(any())).thenReturn(Optional.of(acceptedSignup()));
        when(accountFactory.create(any(),any())).thenReturn(any());

        sut.create(accountCreateCommand,1L);

        verify(accountRepository).save(any());
    }

    private Signup acceptedSignup() {
        Signup signup = signupBuilder.expireDate(LocalDateTime.now().plusDays(1)).build();
        signup.accept();
        return signup;
    }

}