package woowacourse.shoppingcart.domain.customer;

public class Username {

    private final String username;

    public Username(final String username) {
        validateLength(username);
        this.username = username;
    }

    private void validateLength(final String username) {
        if (username.length() < 3 || username.length() > 15) {
            throw new IllegalArgumentException("유저 이름은 3자 이상 15자 이하입니다.");
        }
    }
}
