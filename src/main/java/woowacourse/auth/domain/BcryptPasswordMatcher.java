package woowacourse.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptPasswordMatcher implements PasswordMatcher {

    private final PasswordEncoder encoder;

    public BcryptPasswordMatcher(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public boolean isMatch(String input, String encrypted) {
        return encoder.matches(input, encrypted);
    }
}
