package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    private final String address;

    public Email(String address) {
        validateFormat(address);
        this.address = address;
    }

    private void validateFormat(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 형식이 맞지 않습니다.");
        }
    }

    public String getAddress() {
        return address;
    }
}
