package cart.domain.member;

import cart.exception.InvalidMemberException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private static final String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final int MAX_EMAIL_LENGTH = 50;
    private final String email;

    public Email(String email) {
        this.email = validate(email);
    }

    private String validate(String email) {
        validateNotEmpty(email);
        validateLength(email);
        validateEmailFormat(email);
        return email;
    }

    private void validateNotEmpty(String email) {
        if (email.isEmpty()) {
            throw new InvalidMemberException("이메일 주소를 입력하세요.");
        }
    }

    private void validateLength(String email) {
        int length = email.length();
        if (length > MAX_EMAIL_LENGTH) {
            throw new InvalidMemberException("email은 " + MAX_EMAIL_LENGTH + "자 이하여야 합니다. (현재 " + length + "자)");
        }
    }

    private void validateEmailFormat(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidMemberException("유효하지 않은 email 형식입니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                '}';
    }
}
