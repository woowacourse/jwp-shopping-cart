package woowacourse.member.infrastructure;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import woowacourse.member.exception.PasswordNotValidException;

@Component
public class PasswordValidator {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!?-])[A-Za-z\\d@!?-]{6,20}");

    public void validate(final String password) {
        final Matcher matcher = PASSWORD_PATTERN.matcher(password);
        if (!matcher.matches()) {
            throw new PasswordNotValidException();
        }
    }
}
