package woowacourse.shoppingcart.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private static final Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");

    private final String email;
    private final String nickname;
    private final String password;

    public Customer(String email, String nickname, String password) {
        validateEmailFormat(email);
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    private void validateEmailFormat(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 형식이 맞지 않습니다.");
        }
    }
}
