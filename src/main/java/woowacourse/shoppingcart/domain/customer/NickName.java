package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.customer.InvalidNickNameException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NickName {
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣A-Za-z0-9]{2,8}$");

    private final String value;

    public NickName(String value) {
        validate(value);
        this.value = value;
    }

    public void validate(String value) {
        Matcher matcher = NICKNAME_PATTERN.matcher(value);
        if (!matcher.find()) {
            throw new InvalidNickNameException();
        }
    }

    public String getValue() {
        return value;
    }
}
