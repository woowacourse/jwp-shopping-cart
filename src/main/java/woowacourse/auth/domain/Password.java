package woowacourse.auth.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Password {

    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public EncryptedPassword toEncrypted() {
        MessageDigest messageDigest = newMessageDigest();
        messageDigest.update(value.getBytes());
        return new EncryptedPassword(toEncryptedString(messageDigest.digest()));
    }

    private MessageDigest newMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("암호화 작업을 수행할 수 없습니다.");
        }
    }

    private String toEncryptedString(byte[] hashedValue) {
        return Base64.getEncoder().encodeToString(hashedValue);
    }
}
