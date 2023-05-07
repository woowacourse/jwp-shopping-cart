package cart.domain.member;

import java.util.regex.Pattern;
import org.springframework.util.Assert;

public class Email {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^\\S+@\\S+\\.\\S+$");
    private static final int MAX_EMAIL_LENGTH = 30;
    private final String email;

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        validateBlank(email);
        validateLength(email);
        validateColon(email);
        validateEmailFormat(email);
    }

    private void validateBlank(String email) {
        Assert.hasText(email, "이메일은 빈 값이 될 수 없습니다.");
    }

    private void validateLength(String email) {
        int length = email.length();
        if (length > MAX_EMAIL_LENGTH) {
            throw new IllegalArgumentException("이메일의 길이는 " + MAX_EMAIL_LENGTH + "자리 이하여야 합니다.");
        }
    }

    private void validateColon(String email) {
        if (email.contains(":")) {
            throw new IllegalArgumentException("이메일에 \":\"가 포함될 수 없습니다.");
        }
    }

    private void validateEmailFormat(String email) {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }

    public String getEmail() {
        return email;
    }
}
