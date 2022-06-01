package woowacourse.shoppingcart.domain.customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class NewPassword implements Password {
    private static final String REGULAR_EXPRESSION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern compiledPattern = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_PASSWORD_FORMAT = "올바르지 않은 비밀번호입니다.";

    private final String value;

    public NewPassword(String rawPassword) {
        validatePassword(rawPassword);
        value = encryptPassword(rawPassword);
    }

    private void validatePassword(String rawPassword) {
        if (!compiledPattern.matcher(rawPassword).matches()) {
            throw new IllegalArgumentException(INVALID_PASSWORD_FORMAT);
        }
    }

    private String encryptPassword(String rawPassword) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(rawPassword.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }

    private String bytesToHex(final byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean isSamePassword(final String hashedPassword) {
        return this.value.equals(hashedPassword);
    }

    @Override
    public String getPassword() {
        return value;
    }
}
