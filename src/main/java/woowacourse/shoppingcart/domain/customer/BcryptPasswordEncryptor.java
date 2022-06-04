package woowacourse.shoppingcart.domain.customer;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordEncryptor implements PasswordEncryptor {

    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
