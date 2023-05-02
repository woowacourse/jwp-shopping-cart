package cart.domain;

import static cart.exception.ErrorCode.MEMBER_PASSWORD_LENGTH;

import cart.exception.GlobalException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class MemberPassword {

    private static final int PASSWORD_MIN_LENGTH = 8, PASSWORD_MAX_LENGTH = 50;

    private final String password;

    private MemberPassword(final String password) {
        this.password = password;
    }

    public static MemberPassword create(final String password) {
        validatePassword(password);
        final String encodePassword = encodePassword(password);
        return new MemberPassword(encodePassword);
    }

    public String decodePassword() {
        return new String(Base64.getDecoder().decode(password.getBytes()),
            StandardCharsets.UTF_8);
    }

    private static void validatePassword(final String password) {
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            throw new GlobalException(MEMBER_PASSWORD_LENGTH);
        }
    }

    private static String encodePassword(final String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String getPassword() {
        return password;
    }
}
