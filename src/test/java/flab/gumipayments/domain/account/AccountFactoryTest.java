package flab.gumipayments.domain.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

class AccountFactoryTest {

    @Test
    @DisplayName("성공: 계정 생성을 성공한다.")
    void create() {
        AccountFactory accountFactory = new AccountFactory();
        String password = "password";
        String email = "love4@naver.com";
        String name = "이름";

        Account sut = accountFactory.create(new AccountCreateCommand(password, name), email);

        assertThat(sut.getEmail()).isEqualTo(email);
        assertThat(sut.getName()).isEqualTo(name);
        assertThat(sut.getPassword()).isEqualTo(password);
    }
}