package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private static final Pattern LOWER_CASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPER_CASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHARACTERS_PATTERN = Pattern.compile(".*[!@#$%^&*].*");
    private static final Pattern NUMBERS_PATTERN = Pattern.compile(".*[0-9].*");
    private static final Pattern SIZE_PATTERN = Pattern.compile("^(?=.{8,20}$).*");

    private final String value;

    public Password(String value) {
        validatePasswordFormat(value);
        this.value = value;
    }

    private void validatePasswordFormat(String password) {
        Matcher lowerCaseMatcher = LOWER_CASE_PATTERN.matcher(password);
        Matcher upperCaseMatcher = UPPER_CASE_PATTERN.matcher(password);
        Matcher specialCharactersMatcher = SPECIAL_CHARACTERS_PATTERN.matcher(password);
        Matcher numbersMatcher = NUMBERS_PATTERN.matcher(password);
        Matcher sizeMatcher = SIZE_PATTERN.matcher(password);

        if (!(lowerCaseMatcher.matches() &&
                upperCaseMatcher.matches() &&
                specialCharactersMatcher.matches() &&
                numbersMatcher.matches() &&
                sizeMatcher.matches())) {
            throw new IllegalArgumentException("패스워드 형식이 맞지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
