package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class FakePasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
