package woowacourse.shoppingcart.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Password {

    private String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password from(String password) {
        validatePassword(password);
        String encryptedPassword = encrypt(password);
        return new Password(encryptedPassword);
    }

    private static void validatePassword(String password) {
        if(password.length() < 10){
            throw new IllegalArgumentException("비밀번호 규약이 맞지 않습니다");
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
