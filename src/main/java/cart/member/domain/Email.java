package cart.member.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode
public class Email {
    private static final String EMAIL_FORM = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_FORM);
    private final String email;
    
    public Email(final String email) {
        validateEmail(email);
        this.email = email;
    }
    
    private void validateEmail(final String email) {
        validateBlank(email);
        validateEmailForm(email);
    }
    
    private void validateBlank(final String email) {
        if (Objects.isNull(email) || email.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 이메일이 비어있습니다.");
        }
    }
    
    private void validateEmailForm(final String email) {
        final Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[ERROR] 이메일 형식이 올바르지 않습니다.");
        }
    }
}
