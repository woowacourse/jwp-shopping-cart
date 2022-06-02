package woowacourse.shoppingcart.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Password {

    private static final String PASSWORD_REGEX =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

    private final String password;

    public Password(String password) {
        this.password = password;
    }

    public static Password from(String password) {
        validatePasswordFormat(password);
        return new Password(encryptPassword(password));
    }

    private static void validatePasswordFormat(String password) {
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new IllegalArgumentException("비밀번호의 형식이 맞지 않습니다.");
        }
    }

    private static String encryptPassword(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // or MD5, SHA-1
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
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
}
