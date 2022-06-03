package woowacourse.shoppingcart.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncryptor {

    public static String encrypt(final String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(final String rawPassword, final String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
