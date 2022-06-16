package woowacourse.shoppingcart.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserName {
    private final String userName;

    public UserName(String userName) {
        this.userName = userName;
    }

    public void validateFormat() {
        checkLength();
        checkPattern();
    }

    private void checkLength() {
        if (userName.length() < 5 || userName.length() > 20) {
            throw new IllegalArgumentException("이름은 5자 이상 20자 이하이어야 합니다.");
        }
    }

    private void checkPattern() {
        String regExpPw = "^[a-z0-9_]*$";
        Matcher matcher = Pattern.compile(regExpPw).matcher(userName);
        if(!matcher.find()){
            throw new IllegalArgumentException("이름은 소문자, 숫자, 언더스코어만을 포함할 수 있습니다.");
        }
    }

    public String getName() {
        return userName;
    }
}
