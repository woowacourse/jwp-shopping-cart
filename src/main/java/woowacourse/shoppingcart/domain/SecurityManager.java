package woowacourse.shoppingcart.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityManager {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private SecurityManager() {}

    public static String generateEncodedPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean isSamePassword(String sourcePassword, String targetPassword) {
        return encoder.matches(sourcePassword, targetPassword);
    }
}
