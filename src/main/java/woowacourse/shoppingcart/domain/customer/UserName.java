package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class UserName {

    private static final int USERNAME_MIN_LENGTH = 5;
    private static final int USERNAME_MAX_LENGTH = 20;

    private final String name;

    public UserName(String name) {
        this.name = Objects.requireNonNull(name);
        validateUserName();
    }

    private void validateUserName() {
        if (isConsist() && isLengthRange()) {
            return;
        }
        throw new IllegalArgumentException("아이디는 소문자, 숫자, _ 5~20글자로 구성되어야 합니다.");
    }

    private boolean isConsist() {
        return name.chars()
                .allMatch(ch -> isUnderBar(ch) || isLowerCase(ch) || isDigit(ch));
    }

    private boolean isLowerCase(int ch) {
        return Character.isAlphabetic(ch) && Character.isLowerCase(ch);
    }

    private boolean isDigit(int ch) {
        return Character.isDigit(ch);
    }

    private boolean isUnderBar(int ch) {
        return ch == '_';
    }

    private boolean isLengthRange() {
        return USERNAME_MIN_LENGTH <= name.length() && name.length() <= USERNAME_MAX_LENGTH;
    }

    public String value() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserName userName = (UserName) o;
        return Objects.equals(name, userName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
