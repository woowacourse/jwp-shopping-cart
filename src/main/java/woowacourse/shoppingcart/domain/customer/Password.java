package woowacourse.shoppingcart.domain.customer;

public class Password {

    private static final int MINIMUM_LENGTH = 8;
    private static final int MAXIMUM_LENGTH = 20;

    private final String password;

    public Password(final String password) {
        validateLength(password);
        this.password = password;
    }

    private void validateLength(final String password) {
        if (password.length() < MINIMUM_LENGTH || password.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d자 이상 %d자 이하입니다.", MINIMUM_LENGTH, MAXIMUM_LENGTH));
        }
    }
}
