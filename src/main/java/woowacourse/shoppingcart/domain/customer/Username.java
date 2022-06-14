package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InputFormatException;
import woowacourse.exception.dto.ErrorResponse;

public class Username {

    private static final Pattern PATTERN = Pattern.compile("^.{1,10}$");
    private final String username;

    private Username(String username) {
        this.username = username;
    }

    public static Username of(String username) {
        final String removedBlackUsername = trim(username);
        validateUsername(removedBlackUsername);
        return new Username(removedBlackUsername);
    }

    public String getValue() {
        return username;
    }

    private static void validateUsername(String username) {
        if (!PATTERN.matcher(username).matches()) {
            throw new InputFormatException("닉네임 규약이 맞지 않습니다", ErrorResponse.INVALID_USERNAME);
        }
    }

    private static String trim(String username) {
        return username.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Username)) {
            return false;
        }
        Username username1 = (Username) o;
        return Objects.equals(username, username1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
