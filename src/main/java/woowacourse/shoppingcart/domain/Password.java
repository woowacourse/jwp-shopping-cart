package woowacourse.shoppingcart.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.exception.LoginException;
import woowacourse.exception.dto.ErrorResponse;

public class Password {

    private String password;
    private static final Pattern PATTERN = Pattern.compile("^.*(?=^.{8,12}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");

    private Password(String password) {
        this.password = password;
    }

    public static Password from(String password) {
        validatePassword(password);
        String encryptedPassword = encrypt(password);
        return new Password(encryptedPassword);
    }

    private static void validatePassword(String password) {
        if (!PATTERN.matcher(password).matches()) {
            throw new LoginException("비밀번호 규약이 맞지 않습니다", ErrorResponse.INVALID_PASSWORD);
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

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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
