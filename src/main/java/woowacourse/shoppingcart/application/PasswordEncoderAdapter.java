package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import woowacourse.shoppingcart.domain.Encoder;

public class PasswordEncoderAdapter implements Encoder {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String oldPassword, String newPassword) {
        return passwordEncoder.matches(oldPassword, newPassword);
    }
}
