package woowacourse.fixture;

import woowacourse.shoppingcart.domain.customer.EncryptPassword;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.PasswordSecureHashEncryptor;

public class PasswordFixture {

    public static final String plainBasicPassword = "Aa!45678";
    public static final String plainReversePassword = "87654!aA";
    public static final EncryptPassword encryptedBasicPassword = encode(plainBasicPassword);

    public static final EncryptPassword encryptedReversePassword = encode(plainReversePassword);

    private static EncryptPassword encode(String plainPassword) {
        PasswordEncryptor passwordEncryptor = new PasswordSecureHashEncryptor();
        return new EncryptPassword(passwordEncryptor.encode(plainPassword));
    }
}
