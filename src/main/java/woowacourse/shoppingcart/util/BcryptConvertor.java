package woowacourse.shoppingcart.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.domain.PasswordConvertor;

@Component
public class BcryptConvertor implements PasswordConvertor {

    @Override
    public String encode(final String rawValue) {
        return BCrypt.hashpw(rawValue, BCrypt.gensalt());
    }

    @Override
    public boolean isSamePassword(final String rawValue, final String hashedValue) {
        return BCrypt.checkpw(rawValue, hashedValue);
    }
}
