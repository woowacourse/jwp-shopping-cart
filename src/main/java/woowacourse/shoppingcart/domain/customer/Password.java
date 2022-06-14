package woowacourse.shoppingcart.domain.customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import woowacourse.auth.exception.BadRequestException;

public class Password {
    private static final String REGULAR_EXPRESSION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern compiledPattern = Pattern.compile(REGULAR_EXPRESSION);
    static final String INVALID_PASSWORD_FORMAT = "비밀번호는 8글자 이상 20글자 이하, 영문, 특수문자, 숫자 최소 1개씩 으로 이뤄져야합니다.";

    private final String value;

    public Password(String rawPassword) {
        validatePassword(rawPassword);
        value = encryptPassword(rawPassword);
    }

    private void validatePassword(String rawPassword) {
        if (!compiledPattern.matcher(rawPassword).matches()) {
            throw new BadRequestException(INVALID_PASSWORD_FORMAT);
        }
    }

    private String encryptPassword(String rawPassword) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(rawPassword.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private String bytesToHex(final byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    public boolean isSamePassword(final String hashedPassword) {
        return this.value.equals(hashedPassword);
    }

    public String getPassword() {
        return value;
    }
}
