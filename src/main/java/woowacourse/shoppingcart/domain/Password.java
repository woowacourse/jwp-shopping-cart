package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.custum.InvalidInputException;

public class Password {
    private static final Pattern passwordPattern =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$");


    private final String password;

    public Password(final String password) {
        validatePassword(password);
        this.password = password;
    }

    private void validatePassword(final String password) {
        if (!passwordPattern.matcher(password).matches()) {
            throw new InvalidInputException("패스워드");
        }
    }

    public String get() {
        return password;
    }
}
