package woowacourse.shoppingcart.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.regex.Pattern;

public class PasswordEncryptor {

    private static final String BCRYPT_PATTERN = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

    public static String encrypt(final String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(final String rawPassword, final String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public static boolean isHashed(final String password) {
        return Pattern.matches(BCRYPT_PATTERN, password);
    }
}
