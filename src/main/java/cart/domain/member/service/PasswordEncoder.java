package cart.domain.member.service;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class PasswordEncoder {

    public String encode(final String password) {
        return Base64Utils.encodeToString(password.getBytes());
    }
}
