package cart.user.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Email {
    
    public static final String INVALID_EMAIL_FORMAT_ERROR = "표준 이메일 형식이 아닙니다.";
    private final String value;
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$");
    
    public Email(final String value) {
        this.validate(value);
        this.value = value;
    }
    
    private void validate(final String email) {
        final Matcher matcher = this.pattern.matcher(email);
        if (!matcher.find()) {
            throw new IllegalArgumentException(INVALID_EMAIL_FORMAT_ERROR);
        }
    }
    
    public String getValue() {
        return this.value;
    }
}
