package woowacourse.shoppingcart.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncrypter implements PasswordEncrypter {
    private final PasswordEncoder passwordEncoder;

    public BcryptPasswordEncrypter(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Password encode(CharSequence rawPassword) {
        return new Password(passwordEncoder.encode(rawPassword));
    }

    @Override
    public boolean matches(CharSequence rawPassword, Password encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword.getValue());
    }
}
