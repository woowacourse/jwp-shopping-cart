package woowacourse.fixture;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordFixture {

    public static final String rawBasicPassword = "12345678";
    public static final String encryptedBasicPassword = BCrypt.hashpw(rawBasicPassword, BCrypt.gensalt());
}
