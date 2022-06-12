package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.exception.EncodedPasswordNotCorrectException;
import woowacourse.exception.PasswordInvalidException;
import woowacourse.shoppingcart.application.PasswordEncoderAdapter;

public class Password {

    private static final String ENCODED_PATTERN = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";
    private static final String PLANE_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$";
    private static final Encoder encoder = new PasswordEncoderAdapter();

    private final String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password planePassword(String password) {
        if (!Pattern.matches(PLANE_PATTERN, password)) {
            throw new PasswordInvalidException();
        }
        return new Password(encoder.encode(password));
    }

    public static Password encodePassword(String password) {
        if (!Pattern.matches(ENCODED_PATTERN, password)) {
            throw new EncodedPasswordNotCorrectException();
        }
        return new Password(password);
    }

    public boolean isMatches(String planePassword) {
        return encoder.matches(planePassword, password);
    }

    public String getPassword() {
        return password;
    }
}
