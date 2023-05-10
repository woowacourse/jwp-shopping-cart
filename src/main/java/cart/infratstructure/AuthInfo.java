package cart.infratstructure;

import cart.exception.AuthInfoFormatException;
import java.util.regex.Pattern;

public class AuthInfo {

    private static final Pattern FORMAT_EMAIL = Pattern.compile("^(.+)@(\\S+)$");
    private static final Pattern FORMAT_PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    private static final String EXCEPTION_MESSAGE_WRONG_FORMAT = "잘못된 형식의 사용자 정보입니다";

    private final String email;
    private final String password;

    public AuthInfo(String email, String password) {
        validate(email, password);
        this.email = email;
        this.password = password;
    }

    private void validate(String email, String password) {
        if (!FORMAT_EMAIL.matcher(email).matches()) {
            throw new AuthInfoFormatException(EXCEPTION_MESSAGE_WRONG_FORMAT);
        }
        if (!FORMAT_PASSWORD.matcher(password).matches()) {
            throw new AuthInfoFormatException(EXCEPTION_MESSAGE_WRONG_FORMAT);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
