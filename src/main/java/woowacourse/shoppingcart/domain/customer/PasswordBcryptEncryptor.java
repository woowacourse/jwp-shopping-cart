package woowacourse.shoppingcart.domain.customer;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordBcryptEncryptor implements PasswordEncryptor {

    private final PasswordEncoder passwordEncoder;

    public PasswordBcryptEncryptor(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(final String value) {
        return passwordEncoder.encode(value);
    }

    @Override
    public boolean matches(final String plainPassword, final String encryptPassword) {
        return passwordEncoder.matches(plainPassword, encryptPassword);
    }
}
