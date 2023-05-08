package cart.domain.user;

import cart.exception.user.UserFieldNotValidException;
import java.util.regex.Pattern;

public class UserEmail {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$");

    private final String email;

    public UserEmail(String email) {
        validateEmail(email);

        this.email = email;
    }

    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new UserFieldNotValidException("이메일은 이메일 형식만을 입력할 있습니다.");
        }
    }

    public String getEmail() {
        return email;
    }
}
