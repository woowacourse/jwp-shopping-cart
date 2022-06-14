package woowacourse.shoppingcart.domain.customer;

public class Email {
    private static final String EMAIL_FORMAT = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";

    private final String email;

    public Email(String email) {
        validateEmail(email);
        this.email = email;
    }

    public String getValue() {
        return email;
    }

    private void validateEmail(String email) {
        if (email.isBlank() || !email.matches(EMAIL_FORMAT)) {
            throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다.");
        }
    }
}
