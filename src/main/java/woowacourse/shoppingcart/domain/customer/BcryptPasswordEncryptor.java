package woowacourse.shoppingcart.domain.customer;

import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptPasswordEncryptor implements PasswordEncryptor {

    private final PasswordEncoder encoder;

    public BcryptPasswordEncryptor(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encrypt(String password) {
        return encoder.encode(password);
    }
}
