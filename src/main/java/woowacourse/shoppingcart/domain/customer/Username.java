package woowacourse.shoppingcart.domain.customer;

public class Username {

    private static final int MINIMUM_LENGTH = 3;
    private static final int MAXIMUM_LENGTH = 15;

    private final String username;

    public Username(final String username) {
        validateLength(username);
        this.username = username;
    }

    private void validateLength(final String username) {
        if (username.length() < MINIMUM_LENGTH || username.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("유저 이름은 %d자 이상 %d자 이하입니다.", MINIMUM_LENGTH, MAXIMUM_LENGTH));
        }
    }
}
