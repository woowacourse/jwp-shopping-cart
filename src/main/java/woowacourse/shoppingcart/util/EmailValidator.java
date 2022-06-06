package woowacourse.shoppingcart.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    public static void validate(String email) {
        Matcher matcher = PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("유효한 이메일 형식이 아닙니다.");
        }
    }
}
