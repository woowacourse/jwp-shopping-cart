package woowacourse.shoppingcart.domain.customer;

public class UserName {
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 20;

    private final String userName;

    public UserName(String userName) {
        validateUserName(userName);
        this.userName = userName;
    }

    private void validateUserName(String userName) {
        if (userName.isBlank()) {
            throw new IllegalArgumentException("아이디는 공백일 수 없습니다.");
        }
        if (userName.length() < MIN_USERNAME_LENGTH || userName.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException("아이디의 길이는 4자 이상 20자 이하여야 합니다.");
        }
    }

    public String getUserName() {
        return userName;
    }
}
