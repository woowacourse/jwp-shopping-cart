package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidInformationException;

import java.util.Objects;

public class Email {

    private static final int MAX_EMAIL_SIZE = 64;
    private static final String NOT_NULL_OR_BLANK = "[ERROR] 이메일은 빈 값일 수 없습니다.";
    private static final String NOT_EMAIL_PATTERN = "[ERROR] 이메일 형식이 아닙니다.";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String email;

    public Email(String email) {
        validateNotNull(email);
        validateNotEmpty(email);
        validateNotEmailForm(email);
        validateMaxSize(email);
        this.email = email;
    }

    private void validateNotNull(String email) {
        if (email == null) {
            throw new InvalidInformationException(NOT_NULL_OR_BLANK);
        }
    }

    private void validateNotEmpty(String email) {
        if (email.isEmpty()) {
            throw new InvalidInformationException(NOT_NULL_OR_BLANK);
        }
    }

    private void validateNotEmailForm(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new InvalidInformationException(NOT_EMAIL_PATTERN);
        }
    }

    private void validateMaxSize(String email) {
        if (email.length() > MAX_EMAIL_SIZE) {
            throw new InvalidInformationException("[ERROR] 이메일은 최대 " + MAX_EMAIL_SIZE + "자 이하여야 합니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
