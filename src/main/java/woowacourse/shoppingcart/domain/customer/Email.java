package woowacourse.shoppingcart.domain.customer;

public class Email {
    private static final int UPPER_BOUND_LENGTH = 64;
    private final String email;

    public Email(String email) {
        checkLength(email);
        this.email = email;
    }

    private void checkLength(String email) {
        if (email.length() > UPPER_BOUND_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 이메일의 길이는 64자를 넘을 수 없습니다.");
        }
    }

    public String get() {
        return email;
    }
}
