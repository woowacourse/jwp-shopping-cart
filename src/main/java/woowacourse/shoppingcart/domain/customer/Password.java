package woowacourse.shoppingcart.domain.customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InputFormatException;
import woowacourse.exception.dto.ErrorResponse;

public class Password {

    private static final Pattern PATTERN = Pattern.compile("^.*(?=^.{8,12}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");
    private String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password ofWithEncryption(String password) {
        validatePassword(password);
        return new Password(encrypt(password));
    }

    public static Password ofWithoutEncryption(String password) {
        return new Password(password);
    }

    private static void validatePassword(String password) {
        if (!PATTERN.matcher(password).matches()) {
            throw new InputFormatException("비밀번호 규약이 맞지 않습니다", ErrorResponse.INVALID_PASSWORD);
        }
    }

    private static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public boolean isSame(Password it) {
        return password.equals(it.password);
    }

    public String getValue() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Password)) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
