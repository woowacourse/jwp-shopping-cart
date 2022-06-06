package woowacourse.shoppingcart.domain.customer.password;

import woowacourse.shoppingcart.exception.datanotmatch.CustomerDataNotMatchException;
import woowacourse.shoppingcart.exception.datanotmatch.LoginDataNotMatchException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptedPassword implements Password {

    private final String value;

    public EncryptedPassword(final String value) {
        this.value = value;
    }

    public void validateMatchingLoginPassword(final String other) {
        if (!value.equals(encrypt(other))) {
            throw new LoginDataNotMatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void validateMatchingOriginalPassword(final String other) {
        if (!value.equals(encrypt(other))) {
            throw new CustomerDataNotMatchException("기존 비밀번호와 입력한 비밀번호가 일치하지 않습니다.");
        }
    }

    private String encrypt(final String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    private String bytesToHex(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public String getValue() {
        return value;
    }
}
