package woowacourse.fixture;

import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.shoppingcart.domain.Password;

public class PasswordFixture {

    public static final String rawBasicPassword = "12345678";
    public static final Password encryptedBasicPassword =
            new Password(BCrypt.hashpw(rawBasicPassword, BCrypt.gensalt()));
}
