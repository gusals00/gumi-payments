package flab.gumipayments.infrastructure.encrypt;

import org.apache.hc.client5.http.utils.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class KeyEncrypt {

    private static final String privateKey = "asdf;ghjklaxceghjas2djkla$sdfgha";
    private static final String aesAlg = "AES/CBC/PKCS5Padding";
    private static final String iv = privateKey.substring(0,16);

    public String encrypt(String plainText){
        try {
            SecretKeySpec secretKey = new SecretKeySpec(privateKey.getBytes("UTF-8"),"AES");
            IvParameterSpec IV = new IvParameterSpec(iv.getBytes());

            Cipher c = Cipher.getInstance(aesAlg);
            c.init(Cipher.ENCRYPT_MODE,secretKey,IV);
            byte[] encryptionByte = c.doFinal(plainText.getBytes("UTF-8"));
            return Hex.encodeHexString(encryptionByte);

        }catch (Exception e) {
            throw new RuntimeException("암호화 에러", e);
        }

    }
}
