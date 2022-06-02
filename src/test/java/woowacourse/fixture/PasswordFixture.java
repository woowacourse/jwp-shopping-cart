package woowacourse.fixture;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordFixture {

    public static final String rowBasicPassword = "12345678";
    public static final String encryptedBasicPassword = BCrypt.hashpw(rowBasicPassword, BCrypt.gensalt());
}
