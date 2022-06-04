package woowacourse.auth.domain;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordMatcher implements PasswordMatcher {

    @Override
    public boolean isMatch(String input, String encrypted) {
        return BCrypt.checkpw(input, encrypted);
    }
}
