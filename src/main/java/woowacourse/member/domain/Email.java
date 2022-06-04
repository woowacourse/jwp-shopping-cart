package woowacourse.member.domain;

import woowacourse.member.exception.InvalidMemberEmailException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern pattern = Pattern.compile("^(.+)@(.+)$");

    private final String value;

    public Email(String value) {
        validateForm(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private void validateForm(String email) {
        if (!pattern.matcher(email).find()) {
            throw new InvalidMemberEmailException("올바르지 못한 이메일 형식입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
