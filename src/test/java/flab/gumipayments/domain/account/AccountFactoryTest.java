package flab.gumipayments.domain.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

class AccountFactoryTest {

    @Test
    @DisplayName("계정 생성")
    void create() {
        String password = "password";
        String email = "love4@naver.com";
        String name = "이름";
        AccountFactory accountFactory = new AccountFactory();

        Account account = accountFactory.create(new AccountCreateCommand(password, name), email);

        assertThat(account.getEmail()).isEqualTo(email);
        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getPassword()).isEqualTo(password);
    }
}