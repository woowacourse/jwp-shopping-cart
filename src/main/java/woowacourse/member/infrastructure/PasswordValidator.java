package woowacourse.member.infrastructure;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import woowacourse.member.exception.PasswordNotValidException;

@Component
public class PasswordValidator {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?-])[A-Za-z\\d@!?-]{6,20}";

    public void validate(final String password) {
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new PasswordNotValidException();
        }
    }
}
