package woowacourse.auth.domain;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordMatcher implements PasswordMatcher {

    @Override
    public boolean isMatch(String input, String encrypted) {
        return BCrypt.checkpw(input, encrypted);
    }
}
