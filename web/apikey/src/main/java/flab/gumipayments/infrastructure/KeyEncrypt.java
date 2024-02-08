package flab.gumipayments.infrastructure;

import org.apache.hc.client5.http.utils.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class KeyEncrypt {

    @Value("${encrypt.secret_key}")
    private String privateKey;
    @Value("${encrypt.aes_alg}")
    private String aesAlg;

    public String encrypt(String plainText) {
        String iv = privateKey.substring(0, 16);
        try {
            SecretKeySpec secretKey = new SecretKeySpec(privateKey.getBytes("UTF-8"), "AES");
            IvParameterSpec IV = new IvParameterSpec(iv.getBytes());

            Cipher c = Cipher.getInstance(aesAlg);
            c.init(Cipher.ENCRYPT_MODE, secretKey, IV);
            byte[] encryptionByte = c.doFinal(plainText.getBytes("UTF-8"));
            return Hex.encodeHexString(encryptionByte);

        } catch (Exception e) {
            throw new RuntimeException("암호화 에러", e);
        }
    }
}
