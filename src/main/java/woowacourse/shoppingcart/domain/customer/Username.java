package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Username {
    private static final int MIN_USERNAME_LENGTH = 4;
    private static final int MAX_USERNAME_LENGTH = 20;

    private final String userName;

    public Username(String userName) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username1 = (Username) o;
        return Objects.equals(userName, username1.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
