package flab.gumipayments.infrastructure.encrypt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

class KeyEncryptTest {

    private KeyEncrypt sut = new KeyEncrypt();

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(sut,"privateKey", "asdf;ghjklaxce3456s2djkla$sdfgha");
        ReflectionTestUtils.setField(sut,"aesAlg", "AES/CBC/PKCS5Padding");
    }

    @Test
    @DisplayName("성공: 키 암호화에 성공한다.")
    void encrypt()throws Exception {
        String key = "12345";

        String encryptedKey = sut.encrypt(key);

        assertThat(isEncrypted(key,encryptedKey)).isTrue();
    }

    private boolean isEncrypted(String originalKey,String encryptedKey){
        return !originalKey.equals(encryptedKey);
    }
}