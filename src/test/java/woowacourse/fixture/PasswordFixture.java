package woowacourse.fixture;

import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.shoppingcart.domain.customer.EncryptPassword;

public class PasswordFixture {

    public static final String plainBasicPassword = "Aa!45678";
    public static final String plainReversePassword = "87654!aA";
    public static final EncryptPassword encryptedBasicPassword =
            new EncryptPassword(BCrypt.hashpw(plainBasicPassword, BCrypt.gensalt()));

    public static final EncryptPassword encryptedReversePassword =
            new EncryptPassword(BCrypt.hashpw(plainReversePassword, BCrypt.gensalt()));
}
