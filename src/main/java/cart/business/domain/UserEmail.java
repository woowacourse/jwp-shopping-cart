package cart.business.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserEmail {

    private String email;

    public UserEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바른 이메일을 입력해주세요.");
        }
    }
}
