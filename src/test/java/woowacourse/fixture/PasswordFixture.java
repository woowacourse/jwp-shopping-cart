package woowacourse.fixture;

import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.shoppingcart.domain.Password;

public class PasswordFixture {

    public static final String RAW_BASIC_PASSWORD = "12345678";
    public static final Password ENCRYPTED_BASIC_PASSWORD =
            new Password(BCrypt.hashpw(RAW_BASIC_PASSWORD, BCrypt.gensalt()));
    public static final String ORIGIN_USER_1_PASSWORD = "$2a$10$xqy4zIaYu655Gf.vdKHceecQEuPxOO2kSHwERG7oKJ7nngGbx8eYC";
}
