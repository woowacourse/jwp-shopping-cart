package cart.common;

import org.springframework.util.Base64Utils;

public class PasswordEncoder {

    public String encode(final String password) {
        return Base64Utils.encodeToString(password.getBytes());
    }
}
