package woowacourse.shoppingcart.domain;

public class UserName {

    private static final String regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{3,11}$";

    private final String name;

    public UserName(String name) {
        validateUserName(name);
        this.name = name;
    }

    private void validateUserName(String name) {
        if (name.matches(regex)) {
            return;
        }
        throw new IllegalArgumentException("허용되지 않는 ID 형식입니다.");
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
