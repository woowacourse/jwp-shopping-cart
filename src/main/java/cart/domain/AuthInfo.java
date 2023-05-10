package cart.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthInfo {
    private final String email;
    private final String password;

    public AuthInfo(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
        this.email = email;
        this.password = password;
    }

    private void validateEmail(final String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            return;
        }
        throw new IllegalArgumentException("형식에 맞지 않는 이메일입니다.");
    }

    private void validatePassword(final String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비어 있을 수 업습니다.");
        }

        if (password.length() > 30) {
            throw new IllegalArgumentException("비밀번호는 30자 이하입니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
