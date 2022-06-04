package woowacourse.shoppingcart.domain.customer;

public class UserName {

    private static final String regex = "^[a-z0-9_]{5,20}$";

    private final String name;

    public UserName(String name) {
        validateUserName(name);
        this.name = name;
    }

    private void validateUserName(String name) {
        if (name.matches(regex)) {
            return;
        }
        throw new IllegalArgumentException("아이디는 소문자, 숫자, _ 5~20글자로 구성되어야 합니다.");
    }

    public String value() {
        return name;
    }
}
