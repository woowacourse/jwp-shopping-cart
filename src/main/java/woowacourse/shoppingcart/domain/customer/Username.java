package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Username {

    private static final Pattern PATTERN = Pattern.compile("(\\S)+");
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 10;

    private final String value;

    public Username(String value) {
        validateBlank(value);
        validateLength(value);

        this.value = value;
    }

    private void validateBlank(String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일에는 공백이 들어가면 안됩니다.");
        }
    }

    private void validateLength(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("닉네임 1자 이상 10자 이하여야합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
