package flab.gumipayments.infrastructure.encrypt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class KeyEncryptTest {

    private KeyEncrypt sut = new KeyEncrypt();

    @Test
    @DisplayName("성공: 키 암호화에 성공한다.")
    void encrypt()throws Exception {
        String key = "12345";

        String encryptedKey = sut.encrypt(key);

        assertThat(encryptedKey).isNotEqualTo(key);
    }
}