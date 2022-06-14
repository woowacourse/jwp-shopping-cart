package woowacourse.shoppingcart.domain.customer;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnEncodedPassword {

    private static final Pattern PATTERN = Pattern.compile("\\S*");
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    private final String value;

    public UnEncodedPassword(String value) {
        validateBlank(value);
        validateLength(value);
        this.value = value;
    }

    private void validateBlank(String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호에는 공백이 있으면 안됩니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("비밀번호는 8자 이상 20자 이하여야합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
