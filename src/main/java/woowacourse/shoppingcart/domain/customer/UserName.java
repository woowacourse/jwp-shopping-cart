package woowacourse.shoppingcart.domain.customer;

public class UserName {

    private static final int USERNAME_MIN_LENGTH = 5;
    private static final int USERNAME_MAX_LENGTH = 20;

    private final String name;

    public UserName(String name) {
        this.name = name;
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
                .allMatch(ch -> Validator.isUnderBar(ch) || Validator.isLowerCase(ch) || Validator.isDigit(ch));
    }

    private boolean isLengthRange() {
        return USERNAME_MIN_LENGTH <= name.length() && name.length() <= USERNAME_MAX_LENGTH;
    }

    public String value() {
        return name;
    }
}
